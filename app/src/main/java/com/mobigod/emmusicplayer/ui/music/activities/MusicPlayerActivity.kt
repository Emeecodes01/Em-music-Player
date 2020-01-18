package com.mobigod.emmusicplayer.ui.music.activities

import android.content.ComponentName
import android.media.browse.MediaBrowser
import android.media.browse.MediaBrowser.ConnectionCallback
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaControllerCompat
import com.mobigod.emmusicplayer.base.BaseActivity
import com.mobigod.emmusicplayer.errors.MediaBrowserConnectionFailed
import com.mobigod.emmusicplayer.errors.MediaBrowserConnectionSuspended
import com.mobigod.emmusicplayer.services.EmPlaybackService
import com.mobigod.emmusicplayer.utils.getMediaBrowserObservable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject

class MusicPlayerActivity: BaseActivity() {

    private lateinit var mediaBrowser: MediaBrowserCompat
    private val subscriptions = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mediaBrowser = MediaBrowserCompat(
            this,
            ComponentName(this, EmPlaybackService::class.java),
            connectionCallbacks,
            null)

    }


    private val connectionCallbacks = object : MediaBrowserCompat.ConnectionCallback() {
        override fun onConnected() {
            mediaBrowser.sessionToken.also {
                token ->
                val mediaController = MediaControllerCompat(
                    this@MusicPlayerActivity,
                    token
                )

                MediaControllerCompat.setMediaController(this@MusicPlayerActivity, mediaController)

                buildPlayerControls()
            }
        }

        override fun onConnectionFailed() {
            super.onConnectionFailed()
        }

        override fun onConnectionSuspended() {
            super.onConnectionSuspended()
        }


    }



    private fun buildPlayerControls() {

    }


    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
    }


}