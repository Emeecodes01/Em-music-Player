package com.mobigod.emmusicplayer.songsmanager

import android.util.SparseArray
import androidx.core.util.containsKey
import com.mobigod.emmusicplayer.data.model.Song
import java.util.*
import kotlin.collections.HashMap

class SongsManager: ISongManager {
    val musicQueue = SparseArray<Song>()


    override fun addSongsToQueue(songs: List<Song>) {
        songs.forEach {
            song ->
            musicQueue.put(song.id, song)
        }
    }


    override fun removeSongFromQueue(id: Int) {
        if (musicQueue.containsKey(id))
            musicQueue.delete(id)
    }



    override fun addSongToPlayQueue(song: Song) {
        if (!musicQueue.containsKey(song.id))
            musicQueue.put(song.id, song)
    }


    override fun clearSongsQueue() {
        musicQueue.clear()
    }



}