package com.mobigod.emmusicplayer.di

import com.mobigod.emmusicplayer.ui.music.fragments.AlbumsFragment
import com.mobigod.emmusicplayer.ui.music.fragments.SongsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentsInjectorModule{

    @ContributesAndroidInjector
    abstract fun contributeSongsFragmentInjector(): SongsFragment

    @ContributesAndroidInjector
    abstract fun contributeAlbulmFragmentInjector(): AlbumsFragment
}