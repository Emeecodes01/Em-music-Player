package com.mobigod.emmusicplayer.player

import android.content.Context
import android.media.session.MediaSession
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.media.app.NotificationCompat.MediaStyle
import com.mobigod.emmusicplayer.R
import androidx.media.session.MediaButtonReceiver
import com.mobigod.emmusicplayer.services.EmPlaybackService

class EMPlayerSessionCallback(private val context: Context): MediaSessionCompat.Callback() {
    var ms: MediaSessionCompat? = null

    private val musicNotificationManager = MusicNotificationManager(context)


    override fun onPlay() {
        super.onPlay()

    }

    override fun onPause() {
        super.onPause()
    }

    override fun onSkipToNext() {
        super.onSkipToNext()
    }

    override fun onSkipToPrevious() {
        super.onSkipToPrevious()
    }


    override fun onSetShuffleMode(shuffleMode: Int) {
        super.onSetShuffleMode(shuffleMode)
    }


    override fun onSetRepeatMode(repeatMode: Int) {
        super.onSetRepeatMode(repeatMode)
    }

}