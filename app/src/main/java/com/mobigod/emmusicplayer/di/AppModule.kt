package com.mobigod.emmusicplayer.di

import android.content.Context
import android.os.Environment
import com.google.android.exoplayer2.SimpleExoPlayer
import com.mobigod.emmusicplayer.EMMusicApp
import com.mobigod.emmusicplayer.data.repo.Repository
import com.mobigod.emmusicplayer.rx.ReactivexSchedulers
import com.mobigod.emmusicplayer.songsmanager.SongsManager
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import java.io.File
import javax.inject.Named
import javax.inject.Singleton


@Module
class AppModule {

    @Provides
    @Named("app_name")
    fun provideAppName() = "EM Music Player"


    @Provides
    @Singleton
    fun provideContext(application: EMMusicApp): Context = application


    @Provides
    @Singleton
    fun provideSchedulers() = ReactivexSchedulers()


    @Provides
    @Singleton
    fun provideExternalFileDir(context: Context)
    = context.getExternalFilesDir(null)


    @Provides
    @Singleton
    fun provideSongPlayerManager() = SongsManager()



    @Provides
    @Singleton
    fun provideAppRepository(context: Context, songsManager: SongsManager)
    = Repository(context, songsManager)


    @Provides
    @Singleton
    fun provideExoPlayer(context: Context)
    = SimpleExoPlayer.Builder(context).build()

}