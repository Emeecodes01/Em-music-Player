package com.mobigod.emmusicplayer.player

import android.content.Context
import android.media.MediaPlayer
import android.support.v4.media.MediaMetadataCompat
import androidx.core.util.forEach
import androidx.core.util.isNotEmpty
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.mobigod.emmusicplayer.songsmanager.SongsManager

/**
 * The handles the playback of the player
 */
class MusicPlayerHelper(val context: Context): PlaybackHelper(context) {

    private val player  =  SimpleExoPlayer
        .Builder(context)
        .build()


    fun setMediaSessionConnectorWithPlayer(mediaSessionConnector: MediaSessionConnector,
                                           songsManager: SongsManager) {
        mediaSessionConnector.setPlayer(player)
    }


    override fun playFromMedia(metadata: MediaMetadataCompat) {

    }

    override fun getCurrentMedia(): MediaMetadataCompat {

    }

    override fun isPlaying() =
       player.isPlaying


    override fun onPlay() {
        player.playWhenReady = true
    }

    override fun onPause() {
        player.playWhenReady = false
    }

    override fun onStop() {
        player.stop(true)
        player.release()
    }

    override fun setVolume(volume: Float) {
        player.volume = volume
    }


}