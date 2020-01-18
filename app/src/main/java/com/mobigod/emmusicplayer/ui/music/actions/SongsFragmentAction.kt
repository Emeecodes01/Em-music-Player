package com.mobigod.emmusicplayer.ui.music.actions

import com.mobigod.emmusicplayer.data.model.Song
import com.mobigod.emmusicplayer.mvibase.MviAction

sealed class SongsFragmentAction: MviAction {
    object LoadSongsFromStorageAction: SongsFragmentAction()
    data class AddToQueueAction(val song: Song): SongsFragmentAction()
}