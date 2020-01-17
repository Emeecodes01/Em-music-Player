package com.mobigod.emmusicplayer.ui.music.states

import com.mobigod.emmusicplayer.data.model.Song
import com.mobigod.emmusicplayer.mvibase.MviViewState


data class SongsFragmentState(
    val loading: Boolean,
    val songs: List<Song>,
    val error: Throwable?):
    MviViewState {
    companion object {
        fun initialState() =
            SongsFragmentState(false, emptyList(), null)
    }
}