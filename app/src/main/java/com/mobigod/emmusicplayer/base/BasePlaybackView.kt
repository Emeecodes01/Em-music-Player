package com.mobigod.emmusicplayer.base

import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.fragment.app.Fragment

abstract class BasePlaybackView: Fragment(), PlaybackActions {
    protected var mediaController: MediaControllerCompat? = null
    protected var noOfRepeatClicks = 0
    protected var noOfShuffleClicks = 0


    private val state: Int?
    get() = mediaController?.playbackState?.state


    abstract fun syncMetaData(metadataCompat: MediaMetadataCompat?)
    abstract fun syncPlaybackState(playbackStateCompat: PlaybackStateCompat?)



    fun registerMediaController(mediaControllerCompat: MediaControllerCompat) {
        this.mediaController = mediaControllerCompat
        if (mediaController != null) {
            mediaController?.registerCallback(controllerCallback)
            syncPlaybackState(mediaController?.playbackState)
        }
    }



    protected var controllerCallback = object : MediaControllerCompat.Callback() {
        override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
            syncMetaData(metadata)
        }

        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
            syncPlaybackState(state)
        }
    }


    override fun handlePlayPauseClicked() {
        val state = mediaController?.playbackState?.state
        if (state == PlaybackStateCompat.STATE_PLAYING){
            mediaController?.transportControls?.pause()
        }else {
            mediaController?.transportControls?.play()
        }

        mediaController?.repeatMode
    }

    override fun handleNextClicked() {
        mediaController?.transportControls?.skipToNext()
    }



    override fun handlePreviousClicked() {
        mediaController?.transportControls?.skipToPrevious()
    }



    override fun handleRepeatClicked(times: Int) {
        when {
            times % 3 == 1 -> mediaController?.transportControls?.setRepeatMode(PlaybackStateCompat.REPEAT_MODE_ALL)
            times % 3 == 2 -> mediaController?.transportControls?.setRepeatMode(PlaybackStateCompat.REPEAT_MODE_ONE)
            else -> mediaController?.transportControls?.setRepeatMode(PlaybackStateCompat.REPEAT_MODE_NONE)
        }

    }

    override fun handleShuffleClicked(times: Int) {
        when {
            times % 2 == 1 -> mediaController?.transportControls?.setShuffleMode(PlaybackStateCompat.SHUFFLE_MODE_ALL)
            else -> mediaController?.transportControls?.setShuffleMode(PlaybackStateCompat.SHUFFLE_MODE_NONE)
        }
    }

}