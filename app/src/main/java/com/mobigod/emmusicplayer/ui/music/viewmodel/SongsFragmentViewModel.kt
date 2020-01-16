package com.mobigod.emmusicplayer.ui.music.viewmodel

import androidx.lifecycle.ViewModel
import com.mobigod.emmusicplayer.mvibase.MviViewModel
import com.mobigod.emmusicplayer.ui.music.intents.SongsFragmentIntent
import com.mobigod.emmusicplayer.ui.music.results.SongsFragmentResult
import com.mobigod.emmusicplayer.ui.music.results.SongsFragmentResult.AddToQueueResult
import com.mobigod.emmusicplayer.ui.music.results.SongsFragmentResult.LoadSongsFromStorageResult
import com.mobigod.emmusicplayer.ui.states.SongsFragmentState
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

class SongsFragmentViewModel: ViewModel(), MviViewModel<SongsFragmentIntent, SongsFragmentState> {


    override fun processIntents(intents: Observable<SongsFragmentIntent>) {

    }

    override fun states(): Observable<SongsFragmentState> {

    }

    companion object {
        val reducer = BiFunction {previousViewState: SongsFragmentState, result: SongsFragmentResult -> {
            when(result) {
                is LoadSongsFromStorageResult -> when(result) {
                    is LoadSongsFromStorageResult.Loading -> {
                        previousViewState.copy(loading = true)
                    }
                    is LoadSongsFromStorageResult.Success -> {
                        previousViewState.copy(loading = false, songs = result.songs)
                    }
                    is LoadSongsFromStorageResult.Failure -> {
                        previousViewState.copy(loading = false, error = result.failure)
                    }
                }

                is AddToQueueResult -> when(result) {
                    is AddToQueueResult.Success -> {}
                    is AddToQueueResult.Failure -> {}
                }

            }
        }}
    }

}