package com.mobigod.emmusicplayer.data.repo

import com.mobigod.emmusicplayer.data.model.Song
import io.reactivex.Observable

interface IRepository {
    fun getAllSongsFromStorage(): Observable<List<Song>>
    fun getAlbumArtUri(albumId: Long): Observable<String>


    /**
     * This contains a list of songs last added, and also acts as the default queue
     * Start number is 10
     */
    fun getLastAddedSongs(): Observable<List<Song>>

    fun addSongToQueue(song: Song)
}