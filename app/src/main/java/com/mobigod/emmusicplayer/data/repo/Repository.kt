package com.mobigod.emmusicplayer.data.repo

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import com.mobigod.emmusicplayer.data.model.Song
import com.mobigod.emmusicplayer.songsmanager.SongsManager
import com.mobigod.emmusicplayer.utils.Tools
import io.reactivex.Observable
import java.lang.IllegalStateException
import java.util.*
import javax.inject.Inject
import kotlin.Comparator

class Repository @Inject constructor(private val context: Context, private val songsManager: SongsManager): IRepository {

    override fun getAllSongsFromStorage(): Observable<List<Song>> {
        //use the android content provider to get all songs in the device
        val selection = MediaStore.Audio.Media.IS_MUSIC + " != 0"
        val selectionArgs: Array<String>? = null
        val sortOrder = MediaStore.Audio.Media.DISPLAY_NAME +" DESC"
        val contentResolver = context.contentResolver


        val songListObservable = Observable.create<List<Song>> {
            emitter ->
            val projection = getSongsProjection()
            val songs: MutableList<Song> = mutableListOf()

            var cursor: Cursor? = null

            if (contentResolver != null) {
                cursor = getCursor(cursor, contentResolver, projection, selection, selectionArgs, sortOrder)
            }


            if (cursor != null && cursor.count > 0) {
                if (cursor.moveToFirst()) {
                    construstSongs(cursor, songs)
                }
            }else{
                emitter.onError(Throwable("No songs found."))
            }

            cursor?.close()

            emitter.onNext(songs)
            emitter.onComplete()
        }

        return  songListObservable.flatMap {
            songs ->
            Observable.fromIterable(songs)
                .flatMap {
                    song ->
                    getAlbumArtUri(song.albumId)
                        .map { artUri ->
                            song.copy(albumArt = artUri)
                        }
                        .onErrorReturnItem(song.copy())

                }
                .toList()
                .toObservable()
        }

    }


    override fun getLastAddedSongs(): Observable<List<Song>> {
        return getAllSongsFromStorage()
            .map {songs -> songs.sortedBy { it.dateAdded }.reversed()}
            .flatMap {
                Observable.fromIterable(it)
                    .take(10)
                    .toList()
                    .doOnSuccess {
                        songs ->
                        songsManager.addSongsToQueue(songs)
                    }
                    .toObservable()
            }
    }



    private fun construstSongs(cursor: Cursor, songs: MutableList<Song>) {
        do {
            val songId = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media._ID))
            val artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
            val artistId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID))
            val title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
            val path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
            var displayName =
                cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
            val duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
            val albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID))
            val album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
            val dateAdded = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DATE_ADDED))

            displayName = Tools.getSongNameFromRoughPath(displayName)

            val song = Song(
                songId, artistId, artist,
                title, path, displayName,
                duration, albumId, album, dateAdded = dateAdded)

            songs.add(song)

        } while (cursor.moveToNext())
    }



    private fun getCursor(cursor: Cursor?, contentResolver: ContentResolver, projection: Array<String>, selection: String,
        selectionArgs: Array<String>?,
        sortOrder: String
    ): Cursor? {
        var cursor1 = cursor
        cursor1 = contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )
        return cursor1
    }



    private fun getSongsProjection(): Array<String> {
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ARTIST_ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.DATE_ADDED
        )
        return projection
    }



    override fun getAlbumArtUri(albumId: Long): Observable<String> {
        val contentResolver = context.contentResolver

        return Observable.create {
            emmiter ->

            if (contentResolver != null) {
                val cursor = contentResolver.query(
                    MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                    arrayOf (
                        MediaStore.Audio.Albums._ID,
                        MediaStore.Audio.Albums.ALBUM_ART),
                    MediaStore.Audio.Albums._ID + "=?",
                    arrayOf(albumId.toString()),
                    null
                )
                if (cursor!!.moveToFirst()) {
                    val path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART)) ?: ""

                    if (path.isEmpty())
                        emmiter.onError(IllegalStateException("Can't find an image uri of id: $albumId"))

                    emmiter.onNext(path)
                    emmiter.onComplete()
                }

            }
        }

    }

    override fun addSongToQueue(song: Song) {
        songsManager.addSongToPlayQueue(song)
    }

}