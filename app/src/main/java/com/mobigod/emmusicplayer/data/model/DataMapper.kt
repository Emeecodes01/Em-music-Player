package com.mobigod.emmusicplayer.data.model

import java.io.File

class DataMapper {

    fun songToSongEntityMapper(song: Song): SongEntity {
        return SongEntity()
    }

    fun FileToSongMapper(file: File): Song {
        return Song()
    }
}