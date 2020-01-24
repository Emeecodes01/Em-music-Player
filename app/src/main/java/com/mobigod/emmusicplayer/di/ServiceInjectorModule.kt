package com.mobigod.emmusicplayer.di

import com.mobigod.emmusicplayer.services.EmPlaybackService
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [AppModule::class])
abstract class ServiceInjectorModule {

    @ContributesAndroidInjector
    abstract fun provideSplashActivityModule(): EmPlaybackService

}