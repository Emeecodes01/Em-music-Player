package com.mobigod.emmusicplayer.player

import android.content.Context
import android.media.session.MediaSession
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.mobigod.emmusicplayer.R
import androidx.media.session.MediaButtonReceiver

class EMPlayerSessionCallback(private val context: Context): MediaSessionCompat.Callback() {
    var ms: MediaSessionCompat? = null
    val controller = ms?.controller
    val mediaMetadata = controller?.metadata
    val description = mediaMetadata?.description

    val builder = NotificationCompat.Builder(context, "not_media_channel").apply {
        // Add the metadata for the currently playing track
        setContentTitle(description?.title)
        setContentText(description?.subtitle)
        setSubText(description?.description)
        setLargeIcon(description?.iconBitmap)

        // Enable launching the player by clicking the notification
        setContentIntent(controller?.sessionActivity)


        // Stop the service when the notification is swiped away
//        setDeleteIntent(
//            MediaButtonReceiver.buildMediaButtonPendingIntent(
//                context,
//                PlaybackStateCompat.ACTION_STOP
//            )
//        )

        // Make the transport controls visible on the lockscreen
        setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

        // Add an app icon and set its accent color
        // Be careful about the color
        setSmallIcon(R.drawable.ic_headphones)
        color = ContextCompat.getColor(context, R.color.colorPrimary)

        // Add a pause button
        addAction(
            NotificationCompat.Action(
                R.drawable.pause,
                context.getString(R.string.pause),
                MediaButtonReceiver.buildMediaButtonPendingIntent(
                    context,
                    PlaybackStateCompat.ACTION_PLAY_PAUSE
                )
            )
        )

        // Take advantage of MediaStyle features
        setStyle(androidx.media.app.NotificationCompat.MediaStyle()
            .setMediaSession(ms!!.sessionToken)
            .setShowActionsInCompactView(0)
            // Add a cancel button
            .setShowCancelButton(true)
            .setCancelButtonIntent(
                MediaButtonReceiver.buildMediaButtonPendingIntent(
                    context,
                    PlaybackStateCompat.ACTION_STOP
                )
            )
        )
    }


    override fun onPlay() {
        super.onPlay()

    }


}