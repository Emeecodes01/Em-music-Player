package com.mobigod.emmusicplayer.player

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.media.app.NotificationCompat.MediaStyle
import androidx.media.session.MediaButtonReceiver
import com.mobigod.emmusicplayer.R

class MusicNotificationManager(private val context: Context) {
    private val NOTIFICATION_CHANNEL = "com.mobigod.emmusicplayer.player.notify_chanel101"
    private val TAG = this::class.simpleName

    var notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private val actionPlay: NotificationCompat.Action
        get() {
            return NotificationCompat.Action(
                R.drawable.ic_play_arrow_black_24dp,
                context.getString(R.string.play),
                MediaButtonReceiver.buildMediaButtonPendingIntent(
                    context,
                    PlaybackStateCompat.ACTION_PLAY
                )
            )
        }


    private val actionPause: NotificationCompat.Action
    get() {
        return NotificationCompat.Action (
            R.drawable.ic_pause_black_24dp,
            context.getString(R.string.pause),
            MediaButtonReceiver.buildMediaButtonPendingIntent(
                context,
                PlaybackStateCompat.ACTION_PAUSE
            )
        )
    }


    private val actionNext: NotificationCompat.Action
    get() {
        return NotificationCompat.Action(
            R.drawable.ic_skip_next_black_24dp,
            context.getString(R.string.next),
            MediaButtonReceiver.buildMediaButtonPendingIntent(
                context,
                PlaybackStateCompat.ACTION_PLAY_PAUSE
            )
        )
    }


    private val actionPrevious: NotificationCompat.Action
    get() {
        return NotificationCompat.Action(
            R.drawable.ic_skip_previous_black_24dp,
            context.getString(R.string.previous),
            MediaButtonReceiver.buildMediaButtonPendingIntent(
                context,
                PlaybackStateCompat.ACTION_PLAY_PAUSE
            )
        )
    }

    var notificationBuilder: NotificationCompat.Builder



    init {
        notificationBuilder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL)
    }


    fun getNotification(metadata: MediaMetadataCompat,
                        controller: MediaControllerCompat, state: PlaybackStateCompat, token: MediaSessionCompat.Token): Notification {
        val notificationBuilder = buildNotification(metadata, controller, state, token)
        return notificationBuilder.build()
    }

    private fun buildNotification(metadata: MediaMetadataCompat,
                                  controller: MediaControllerCompat, state: PlaybackStateCompat, token: MediaSessionCompat.Token)
    : NotificationCompat.Builder {
        if (isAndroidOOrHigher())
            createChannel()


        val description = metadata.description
        return notificationBuilder.apply {
            // Add the metadata for the currently playing track
            setStyle(MediaStyle()
                    .setMediaSession(token)
                    .setShowActionsInCompactView(0, 1, 2)
                    // For backwards compatibility with Android L and earlier.
                    .setShowCancelButton(true)
                    .setCancelButtonIntent(
                        MediaButtonReceiver.buildMediaButtonPendingIntent(
                            context,
                            PlaybackStateCompat.ACTION_STOP)))
            setContentTitle(description?.title)
            setContentText(description?.subtitle)
            setSubText(description?.description)
            setLargeIcon(description?.iconBitmap)

            // Enable launching the player by clicking the notification
            setContentIntent(controller.sessionActivity)

            // Stop the service when the notification is swiped away
            setDeleteIntent(
                MediaButtonReceiver.buildMediaButtonPendingIntent(
                    context,
                    PlaybackStateCompat.ACTION_STOP
                )
            )

            // Make the transport controls visible on the lockscreen
            setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

            // Add an app icon and set its accent color
            // Be careful about the color
            setSmallIcon(R.drawable.ic_headphones)

            color = ContextCompat.getColor(context, R.color.colorPrimary)
            if (state.state == PlaybackStateCompat.STATE_PLAYING)
                addAction(actionPause)
            else
                addAction(actionPlay)

        }
    }

    private fun isAndroidOOrHigher() =
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.O



    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        if (notificationManager.getNotificationChannel(NOTIFICATION_CHANNEL) == null) {
            // The user-visible name of the channel.
            val name = "MediaSession"
            // The user-visible description of the channel.
            val description = "MediaSession and MediaPlayer"
            val importance = NotificationManager.IMPORTANCE_LOW
            val mChannel = NotificationChannel(NOTIFICATION_CHANNEL, name, importance)
            // Configure the notification channel.
            mChannel.description = description
            //mChannel.enableLights(true)
            // Sets the notification light color for notifications posted to this
            // channel, if the device supports this feature.
           // mChannel.lightColor = Color.RED
//            mChannel.enableVibration(true)
//            mChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            notificationManager.createNotificationChannel(mChannel)
            Log.d(TAG, "createChannel: New channel created")
        } else {
            Log.d(TAG, "createChannel: Existing channel reused")
        }
    }
}