package com.mobigod.emmusicplayer.ui.music

import com.mobigod.emmusicplayer.data.repo.Repository
import com.mobigod.emmusicplayer.rx.ReactivexSchedulers
import com.mobigod.emmusicplayer.ui.music.processors.SongsFragmentProcessorHolder
import dagger.Module
import dagger.Provides

@Module
class MusicModule {

    @Provides
    fun provideSongsFragmentProcessor(repository: Repository, schedulers: ReactivexSchedulers)
            = SongsFragmentProcessorHolder(repository, schedulers)

}