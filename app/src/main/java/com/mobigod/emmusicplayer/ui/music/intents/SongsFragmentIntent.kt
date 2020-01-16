package com.mobigod.emmusicplayer.ui.music.intents

import com.mobigod.emmusicplayer.mvibase.MviIntent

sealed class SongsFragmentIntent: MviIntent {
    object LoadAllSongsFromStorageIntent: SongsFragmentIntent()
}