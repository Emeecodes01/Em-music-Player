package com.mobigod.emmusicplayer.songsmanager

import com.mobigod.emmusicplayer.data.model.Song

interface ISongManager {
    fun addSongToPlayQueue(song: Song)
    fun removeSongFromQueue(id: Int)
    fun clearSongsQueue()
    fun addSongsToQueue(songs: List<Song>)
}