package com.mobigod.emmusicplayer.songsmanager

import com.mobigod.emmusicplayer.data.model.Song
import java.util.*

class SongsManager: ISongManager{


    private val songQueue = LinkedList<Song>()
    private val playedSongs = Stack<Song>()

    override fun addSongToPlayQueue(song: Song) {

    }

    override fun addSongToPlayedQueue(song: Song) {

    }

    override fun addAllSongToQueue(vararg songs: Song) {

    }

    override fun clearSongsQueue() {

    }

    override fun clearPlayedSongs() {

    }
}