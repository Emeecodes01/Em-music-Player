package com.mobigod.emmusicplayer.services

import android.media.browse.MediaBrowser
import android.media.session.MediaSession
import android.media.session.PlaybackState
import android.os.Bundle
import android.service.media.MediaBrowserService
import com.mobigod.emmusicplayer.player.EMPlayerSessionCallback

//import android.support.v4.media.session.MediaSessionCompat

class EmPlaybackService: MediaBrowserService() {

    private var mediaSession: MediaSession? = null
    lateinit var playBackState: PlaybackState.Builder
    private val MEDIA_TAG = "MediaBrowserService"
    private val MY_EMPTY_MEDIA_ROOT_ID = "empty_root_id"


    override fun onCreate() {
        super.onCreate()


        mediaSession = MediaSession(this, MEDIA_TAG).apply {

            setFlags(MediaSession.FLAG_HANDLES_MEDIA_BUTTONS
                    or MediaSession.FLAG_HANDLES_TRANSPORT_CONTROLS
            )

            playBackState = PlaybackState.Builder()
                .setActions(PlaybackState.ACTION_PLAY or PlaybackState.ACTION_PAUSE)


            setPlaybackState(playBackState.build())

            setCallback(EMPlayerSessionCallback(this@EmPlaybackService)
                .apply {
                    ms = mediaSession
                })

            setSessionToken(sessionToken)
        }
    }

    override fun onLoadChildren(parentId: String, result: Result<MutableList<MediaBrowser.MediaItem>>) {

    }

    override fun onGetRoot(clientPackageName: String, clientUid: Int, rootHints: Bundle?): BrowserRoot? {
        return BrowserRoot(MY_EMPTY_MEDIA_ROOT_ID, null)
    }

}