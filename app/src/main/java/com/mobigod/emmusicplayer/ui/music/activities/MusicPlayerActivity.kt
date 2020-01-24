package com.mobigod.emmusicplayer.ui.music.activities

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.media.browse.MediaBrowser
import android.media.browse.MediaBrowser.ConnectionCallback
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaControllerCompat
import com.mobigod.emmusicplayer.R
import com.mobigod.emmusicplayer.base.BaseActivity
import com.mobigod.emmusicplayer.base.BaseFragmentActivity
import com.mobigod.emmusicplayer.data.model.Song
import com.mobigod.emmusicplayer.errors.MediaBrowserConnectionFailed
import com.mobigod.emmusicplayer.errors.MediaBrowserConnectionSuspended
import com.mobigod.emmusicplayer.services.EmPlaybackService
import com.mobigod.emmusicplayer.ui.music.fragments.musicviews.CircularMusicViewFragment
import com.mobigod.emmusicplayer.utils.getMediaBrowserObservable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject

class MusicPlayerActivity: BaseFragmentActivity() {

    private lateinit var mediaBrowser: MediaBrowserCompat
    private val subscriptions = CompositeDisposable()
    private lateinit var circularMusicViewFragment: CircularMusicViewFragment
    private var song: Song? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_player)

        song = intent.getParcelableExtra(MusicActivity.SONG_ARG)

        initAllPlayerViews()

        mediaBrowser = MediaBrowserCompat(
            this,
            ComponentName(this, EmPlaybackService::class.java),
            connectionCallbacks,
            null)

        loadPlayerView()
    }


    private fun loadPlayerView() {
        startFragment(
            R.id.musicview_frag_container, circularMusicViewFragment,
            CircularMusicViewFragment::class.simpleName
        )
    }

    private fun initAllPlayerViews() {
        circularMusicViewFragment = CircularMusicViewFragment.getInstance(song)
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
        val mediaController = MediaControllerCompat.getMediaController(this)
        circularMusicViewFragment.registerMediaController(mediaController)
    }


    override fun onStart() {
        super.onStart()
        mediaBrowser.connect()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
       // MediaControllerCompat.getMediaController(this)?.unregisterCallback(controllerCallback)
        mediaBrowser.disconnect()
    }




    companion object {
        fun start(context: Context, song: Song) {
            Intent(context, MusicPlayerActivity::class.java).apply {
                putExtra(MusicActivity.SONG_ARG, song)
            }.also {
                context.startActivity(it)
            }
        }
    }
}