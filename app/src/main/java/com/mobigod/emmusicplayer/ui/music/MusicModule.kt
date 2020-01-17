package com.mobigod.emmusicplayer.ui.music

import com.mobigod.emmusicplayer.data.repo.Repository
import com.mobigod.emmusicplayer.rx.ReactivexSchedulers
import com.mobigod.emmusicplayer.ui.music.modules.SongsModule
import com.mobigod.emmusicplayer.ui.music.processors.SongsFragmentProcessorHolder
import com.mobigod.emmusicplayer.ui.music.viewmodel.SongsFragmentViewModel
import dagger.Module
import dagger.Provides

@Module(includes = [SongsModule::class])
class MusicModule {


}