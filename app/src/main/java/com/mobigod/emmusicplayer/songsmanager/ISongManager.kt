package com.mobigod.emmusicplayer.songsmanager

import android.content.Context
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.session.MediaSessionCompat
import com.google.android.exoplayer2.source.MediaSource
import com.mobigod.emmusicplayer.data.model.Song

interface ISongManager {
    fun addSongToPlayQueue(song: Song)
    fun removeSongFromQueue(id: Int)
    fun clearSongsQueue()
    fun addSongsToQueue(songs: List<Song>)
    fun createQueueFromSongs(mediaSessionCompat: MediaSessionCompat): List<MediaSessionCompat.QueueItem>
    fun getMediaSources(context: Context): MediaSource
    fun getMediaDescription(context: Context, position: Int): MediaDescriptionCompat
}