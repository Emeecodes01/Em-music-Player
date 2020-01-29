package com.mobigod.emmusicplayer.songsmanager

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.util.SparseArray
import androidx.core.util.containsKey
import androidx.core.util.forEach
import androidx.core.util.isNotEmpty
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.mobigod.emmusicplayer.BuildConfig
import com.mobigod.emmusicplayer.data.model.Song
import com.mobigod.emmusicplayer.utils.Tools
import java.util.*
import kotlin.collections.HashMap

class SongsManager: ISongManager {
    val musicQueue = SparseArray<Song>()
    private var listener: SongsEventListener? = null


    override fun addSongsToQueue(songs: List<Song>) {
        songs.forEach {
            song ->
            musicQueue.put(song.id, song)
        }
        listener?.onQueueChange()
    }


    override fun removeSongFromQueue(id: Int) {
        if (musicQueue.containsKey(id)) {
            musicQueue.delete(id)
            listener?.onQueueChange()
        }
    }



    override fun addSongToPlayQueue(song: Song) {
        if (!musicQueue.containsKey(song.id)){
            musicQueue.put(song.id, song)
            listener?.onQueueChange()
        }
    }


    override fun clearSongsQueue() {
        musicQueue.clear()
    }

    fun setSongQueueChangeListener(listener: SongsEventListener?) {
        this.listener = listener
        if (musicQueue.isNotEmpty())
            listener?.onQueueChange()
    }


    override fun createQueueFromSongs(mediaSessionCompat: MediaSessionCompat): List<MediaSessionCompat.QueueItem> {
        val queue : MutableList<MediaSessionCompat.QueueItem> = mutableListOf()
        musicQueue.forEach { key, value ->
            val description = MediaDescriptionCompat.Builder()
                .apply {
                setMediaId(key.toString())
                setDescription(value.title)
                setTitle(value.title)
                setSubtitle(value.artist)
                setIconUri(Uri.parse(value.albumArt))
                setMediaUri(Uri.parse(value.path))
            }.build()

            val queueItem = MediaSessionCompat.QueueItem(description, key.toLong())
            queue.add(queueItem)
        }
        return queue.toList()
    }



    override fun getMediaSources(context: Context): MediaSource {
        val dataSourceFactory = DefaultDataSourceFactory(context, Util.getUserAgent(context, BuildConfig.APP_NAME))
        val concatinatingMediaSource = ConcatenatingMediaSource()

        musicQueue.forEach { key, value ->
            val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(value.path))
            concatinatingMediaSource.addMediaSource(mediaSource)
        }

        return concatinatingMediaSource
    }


    override fun getMediaDescription(context: Context, position: Int): MediaDescriptionCompat {
        val song = musicQueue.valueAt(position)
        val bundle = Bundle()
        val imageBitmap = Tools.getBitmapFromPath(context, song.path)
        bundle.putParcelable(MediaMetadataCompat.METADATA_KEY_ALBUM_ART, imageBitmap)
        bundle.putParcelable(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON, imageBitmap)

        return MediaDescriptionCompat.Builder()
            .apply {
                setMediaId(song.id.toString())
                setIconBitmap(imageBitmap)
                setTitle(song.title)
                setDescription(song.artist)
                setExtras(bundle)
            }.build()
    }


    interface SongsEventListener {
        fun onQueueChange()
    }

}