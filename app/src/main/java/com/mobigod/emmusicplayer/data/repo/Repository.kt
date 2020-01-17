package com.mobigod.emmusicplayer.data.repo

import android.content.Context
import android.database.Cursor
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import com.mobigod.emmusicplayer.data.model.Song
import com.mobigod.emmusicplayer.songsmanager.SongsManager
import io.reactivex.Observable
import java.io.File
import javax.inject.Inject

class Repository @Inject constructor(val context: Context, val songsManager: SongsManager): IRepository {

    override fun getAllSongsFromStorage(): Observable<List<Song>> {
        //use the android content provider to get all songs in the device
        val selection = MediaStore.Audio.Media.IS_MUSIC + " != 0"
        val selectionArgs: Array<String>? = null
        val contentResolver = context.contentResolver
        val songs: MutableList<Song> = mutableListOf()


        val projection = arrayOf (
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ARTIST_ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.ALBUM
        )


        var cursor: Cursor? = null

        if (contentResolver != null) {
            cursor = contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                MediaStore.Audio.Media.DISPLAY_NAME
            )
        }


        if (cursor != null && cursor.count > 0) {
            if (cursor.moveToFirst()){
                do {
                    val artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                    val artistId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID))
                    val title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                    val path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                    val displayName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
                    val duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
                    val albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID))
                    val album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))

                    val song = Song(artistId, artist, title, path, displayName, duration, albumId, album)
                    songs.add(song)

                } while (cursor.moveToNext())
            }
        }else{
            return Observable.error(Throwable("No songs found."))
        }

        cursor.close()
        return Observable.just(songs)
    }




}