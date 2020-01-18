package com.mobigod.emmusicplayer.ui.music.modules

import com.mobigod.emmusicplayer.data.repo.Repository
import com.mobigod.emmusicplayer.rx.ReactivexSchedulers
import com.mobigod.emmusicplayer.ui.music.processors.SongsFragmentProcessorHolder
import com.mobigod.emmusicplayer.ui.music.viewmodel.SongsFragmentViewModel
import dagger.Module
import dagger.Provides

@Module
class SongsModule {

    @Provides
    fun provideSongsFragmentProcessor(repository: Repository, schedulers: ReactivexSchedulers)
            = SongsFragmentProcessorHolder(repository, schedulers)

    @Provides
    fun provideSongsFragmentViewModel(processorHolder: SongsFragmentProcessorHolder)
            = SongsFragmentViewModel(processorHolder)
    
}