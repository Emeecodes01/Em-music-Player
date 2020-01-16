package com.mobigod.emmusicplayer.ui.music.results

import com.mobigod.emmusicplayer.data.model.Song
import com.mobigod.emmusicplayer.mvibase.MviResult

sealed class SongsFragmentResult: MviResult {

    sealed class LoadSongsFromStorageResult: SongsFragmentResult() {
        object Loading: LoadSongsFromStorageResult()
        data class Success(val songs: List<Song>): LoadSongsFromStorageResult()
        data class Failure(val failure: Throwable): LoadSongsFromStorageResult()
    }


    sealed class AddToQueueResult: SongsFragmentResult() {
        object Success: AddToQueueResult()
        data class Failure(val fail: Throwable): AddToQueueResult()
    }

}