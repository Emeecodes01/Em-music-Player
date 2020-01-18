package com.mobigod.emmusicplayer.utils

import android.content.ComponentName
import android.content.Context
import android.support.v4.media.MediaBrowserCompat
import com.mobigod.emmusicplayer.errors.MediaBrowserConnectionFailed
import com.mobigod.emmusicplayer.errors.MediaBrowserConnectionSuspended
import com.mobigod.emmusicplayer.services.EmPlaybackService
import com.mobigod.emmusicplayer.ui.music.activities.MusicPlayerActivity
import io.reactivex.Observable



fun MusicPlayerActivity.getMediaBrowserObservable(): Observable<Unit> {

    return Observable.create {

        emitter ->
        val mbc = MediaBrowserCompat(
            this,
            ComponentName(this, EmPlaybackService::class.java),
            object : MediaBrowserCompat.ConnectionCallback() {
                override fun onConnected() {
                    emitter.onNext(Unit)
                    emitter.onComplete()
                }

                override fun onConnectionFailed() {
                    emitter.onError(MediaBrowserConnectionFailed())
                }

                override fun onConnectionSuspended() {
                    emitter.onError(MediaBrowserConnectionSuspended())
                }

            }, null
            )

    }

}