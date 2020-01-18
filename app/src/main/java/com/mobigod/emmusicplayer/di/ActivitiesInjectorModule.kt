package com.mobigod.emmusicplayer.di

import com.mobigod.emmusicplayer.ui.SplashScreen
import com.mobigod.emmusicplayer.ui.music.MusicModule
import com.mobigod.emmusicplayer.ui.music.activities.MusicActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesInjectorModule {

    @ContributesAndroidInjector
    abstract fun provideSplashActivityModule(): SplashScreen

    @ContributesAndroidInjector(modules = [MusicModule::class])
    abstract fun provideMusicActivityInjectorModule(): MusicActivity

}