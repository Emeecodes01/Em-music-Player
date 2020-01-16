package com.mobigod.emmusicplayer.data.repo

import com.mobigod.emmusicplayer.data.model.Song
import io.reactivex.Observable

interface IRepository {
    fun getAllSongsFromStorage(): Observable<List<Song>>
}