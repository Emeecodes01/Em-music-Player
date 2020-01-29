package com.mobigod.emmusicplayer.services

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.browse.MediaBrowser
import android.os.Bundle
import android.service.media.MediaBrowserService
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.media.MediaBrowserServiceCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.google.android.exoplayer2.ext.mediasession.TimelineQueueNavigator
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerControlView
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.mobigod.emmusicplayer.R
import com.mobigod.emmusicplayer.player.EMPlayerSessionCallback
import com.mobigod.emmusicplayer.player.MusicNotificationManager
import com.mobigod.emmusicplayer.player.PlaybackHelper
import com.mobigod.emmusicplayer.songsmanager.SongsManager
import com.mobigod.emmusicplayer.ui.music.activities.MusicPlayerActivity
import com.mobigod.emmusicplayer.utils.Tools
import dagger.android.AndroidInjection
import dagger.android.DaggerService
import java.util.ArrayList
import javax.inject.Inject

//import android.support.v4.media.session.MediaSessionCompat

class EmPlaybackService: MediaBrowserServiceCompat(), SongsManager.SongsEventListener {

    @Inject
    lateinit var songsManager: SongsManager

    private lateinit var playerNotificationManager: PlayerNotificationManager
    lateinit var mMediaSession: MediaSessionCompat
    private val MEDIA_TAG = "MediaBrowserService"
    private val MY_EMPTY_MEDIA_ROOT_ID = "empty_root_id"
    private lateinit var mediaSessionConnector: MediaSessionConnector
    lateinit var playerControlView: PlayerControlView



    private val player = SimpleExoPlayer.Builder(this)
        .setTrackSelector(DefaultTrackSelector(this))
        .build()


    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()

        songsManager.setSongQueueChangeListener(this)

        playerNotificationManager = PlayerNotificationManager.createWithNotificationChannel(this,
            "channel_id", R.string.notification_chal_name, R.string.notification_chal_des,
            1, PlayerNotificationDescription(), PlayerNotificationListener())

        playerNotificationManager.setPlayer(player)

        createMediaSessionCompact()
        playerNotificationManager.setMediaSessionToken(mMediaSession.sessionToken)

        setUpMediaConnector()
    }


    override fun onQueueChange() {
        //TODO: This code might take a while to execute, pls make this async
        player.prepare(songsManager.getMediaSources(this@EmPlaybackService))
        player.playWhenReady = true
    }


    private fun setUpMediaConnector() {
        mediaSessionConnector = MediaSessionConnector(mMediaSession)
        mediaSessionConnector.setQueueNavigator(MusicQueueNavigator())
        mediaSessionConnector.setPlayer(player)
    }


    private fun createMediaSessionCompact() {
        mMediaSession = MediaSessionCompat(this, MEDIA_TAG)
        mMediaSession.isActive = true
    }


    override fun onLoadChildren(parentId: String, result: Result<MutableList<MediaBrowserCompat.MediaItem>>) {

    }

    override fun onGetRoot(clientPackageName: String, clientUid: Int, rootHints: Bundle?): BrowserRoot? {
        return BrowserRoot(MY_EMPTY_MEDIA_ROOT_ID, null)
    }



    inner class PlayerNotificationListener: PlayerNotificationManager.NotificationListener {
        override fun onNotificationPosted(notificationId: Int, notification: Notification, ongoing: Boolean){

            if (!ongoing)
                startForeground(notificationId, notification)
        }


        override fun onNotificationCancelled(notificationId: Int, dismissedByUser: Boolean) {
            stopSelf()
        }

    }



    inner class PlayerNotificationDescription: PlayerNotificationManager.MediaDescriptionAdapter {
        override fun createCurrentContentIntent(player: Player): PendingIntent? {
            val intent = Intent(this@EmPlaybackService, MusicPlayerActivity::class.java)
            return PendingIntent.getActivity(this@EmPlaybackService, 0, intent, 0)
        }

        override fun getCurrentContentText(player: Player): String? {
            val song = songsManager.musicQueue.valueAt(player.currentWindowIndex)
            return song.artist
        }

        override fun getCurrentContentTitle(player: Player): String {
            val song = songsManager.musicQueue.valueAt(player.currentWindowIndex)
            return song.title
        }

        override fun getCurrentLargeIcon(player: Player, callback: PlayerNotificationManager.BitmapCallback): Bitmap? {
            Glide.with(this@EmPlaybackService)
                .asBitmap()
                .listener(object : RequestListener<Bitmap> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
                        return false
                    }

                    override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        callback.onBitmap(resource!!)
                        return false
                    }

                }).submit()

            return null
        }

    }



    inner class MusicQueueNavigator : TimelineQueueNavigator(mMediaSession) {
        override fun getMediaDescription(player: Player, windowIndex: Int): MediaDescriptionCompat {
            return songsManager.getMediaDescription(this@EmPlaybackService, windowIndex)
        }
    }


    override fun onDestroy() {
        freeResources()
        super.onDestroy()
    }


    private fun freeResources() {
        mMediaSession.release()
        mediaSessionConnector.setPlayer(null)
        playerNotificationManager.setPlayer(null)
        player.release()
        songsManager.setSongQueueChangeListener(null)
    }

}
