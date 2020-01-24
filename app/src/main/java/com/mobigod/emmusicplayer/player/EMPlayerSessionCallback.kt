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
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.google.android.exoplayer2.source.MediaSource
import com.mobigod.emmusicplayer.services.EmPlaybackService
import com.mobigod.emmusicplayer.songsmanager.SongsManager

/**
 * This class basically do the following
 * (1) Controls how to start or stop a service,
 * (2) Controls notifications
 *
 */
class EMPlayerSessionCallback(private val service: EmPlaybackService,
                              val connector: MediaSessionConnector, val songsManager: SongsManager) {

    private val musicNotificationManager = MusicNotificationManager(service)
    private val musicPlaybackHelper = MusicPlayerHelper(service)

    init {
        musicPlaybackHelper.setMediaSessionConnectorWithPlayer(connector, songsManager)
    }



}