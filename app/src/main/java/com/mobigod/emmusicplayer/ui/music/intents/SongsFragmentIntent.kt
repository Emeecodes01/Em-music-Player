package com.mobigod.emmusicplayer.ui.music.intents

import com.mobigod.emmusicplayer.data.model.Song
import com.mobigod.emmusicplayer.mvibase.MviIntent

sealed class SongsFragmentIntent: MviIntent {
    object LoadAllSongsFromStorageIntent: SongsFragmentIntent()
    data class AddToQueueIntent(val song: Song): SongsFragmentIntent()
}