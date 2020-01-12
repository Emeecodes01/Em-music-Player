package com.mobigod.emmusicplayer.di

import android.content.Context
import com.mobigod.emmusicplayer.EMMusicApp
import dagger.Module
import dagger.Provides
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


}