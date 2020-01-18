package com.mobigod.emmusicplayer.songsmanager

import com.mobigod.emmusicplayer.data.model.Song

interface ISongManager {
    fun addSongToPlayQueue(song: Song)
    fun addSongToPlayedQueue(song: Song)
    fun addAllSongToQueue(vararg songs: Song)
    fun clearSongsQueue()
    fun clearPlayedSongs()
}