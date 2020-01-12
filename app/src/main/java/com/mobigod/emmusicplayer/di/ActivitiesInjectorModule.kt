package com.mobigod.emmusicplayer.di

import com.mobigod.emmusicplayer.ui.SplashScreen
import com.mobigod.emmusicplayer.ui.music.activities.MusicActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesInjectorModule {

    @ContributesAndroidInjector
    abstract fun provideSplashActivityModule(): SplashScreen

    @ContributesAndroidInjector
    abstract fun provideMusicActivityInjectorModule(): MusicActivity

}