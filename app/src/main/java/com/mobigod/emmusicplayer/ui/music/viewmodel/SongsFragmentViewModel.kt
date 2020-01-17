package com.mobigod.emmusicplayer.ui.music.viewmodel

import androidx.lifecycle.ViewModel
import com.mobigod.emmusicplayer.mvibase.MviViewModel
import com.mobigod.emmusicplayer.ui.music.actions.SongsFragmentAction
import com.mobigod.emmusicplayer.ui.music.intents.SongsFragmentIntent
import com.mobigod.emmusicplayer.ui.music.processors.SongsFragmentProcessorHolder
import com.mobigod.emmusicplayer.ui.music.results.SongsFragmentResult
import com.mobigod.emmusicplayer.ui.music.results.SongsFragmentResult.AddToQueueResult
import com.mobigod.emmusicplayer.ui.music.results.SongsFragmentResult.LoadSongsFromStorageResult
import com.mobigod.emmusicplayer.ui.music.states.SongsFragmentState
import com.mobigod.emmusicplayer.utils.notOfType
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class SongsFragmentViewModel @Inject constructor(protected val songsProcessorHolder: SongsFragmentProcessorHolder):
    ViewModel(), MviViewModel<SongsFragmentIntent, SongsFragmentState> {

    private val intentSubject = PublishSubject.create<SongsFragmentIntent>()
    private val statesObservable: Observable<SongsFragmentState> = compose()


    private val intentFilter: ObservableTransformer<SongsFragmentIntent, SongsFragmentIntent>
    get() = ObservableTransformer {intents ->
        intents.publish{ shared ->
            Observable.merge(
                shared.ofType(SongsFragmentIntent.LoadAllSongsFromStorageIntent::class.java).take(1),
                shared.notOfType(SongsFragmentIntent.LoadAllSongsFromStorageIntent::class.java)
            )
        }
    }


    override fun processIntents(intents: Observable<SongsFragmentIntent>) {
        intents.subscribe(intentSubject)
    }

    override fun states(): Observable<SongsFragmentState> = statesObservable


    private fun compose(): Observable<SongsFragmentState> {
        return intentSubject
            .compose(intentFilter)
            .map(this::actionsFromIntent)
            .compose(songsProcessorHolder.actionProcessor)
            .scan(SongsFragmentState.initialState(), reducer)
            .distinctUntilChanged()
            .replay(1)
            .autoConnect(0)

    }


    private fun actionsFromIntent(intent: SongsFragmentIntent): SongsFragmentAction {
        return when(intent) {
            is SongsFragmentIntent.LoadAllSongsFromStorageIntent -> SongsFragmentAction.LoadSongsFromStorageAction
            is SongsFragmentIntent.AddToQueueIntent -> SongsFragmentAction.AddToQueueAction(intent.song)
        }
    }


    companion object {
        private val reducer = BiFunction { previousState: SongsFragmentState, result: SongsFragmentResult ->
            when (result) {
                is LoadSongsFromStorageResult -> when (result) {
                    is LoadSongsFromStorageResult.Loading -> {
                        previousState.copy(loading = true, songs = emptyList())
                    }
                    is LoadSongsFromStorageResult.Failure ->
                        previousState.copy(loading = false, error = result.failure)
                    is LoadSongsFromStorageResult.Success ->
                        previousState.copy(loading = false, songs = result.songs)
                }

                is AddToQueueResult -> when (result) {
                    is AddToQueueResult.Success -> {
                        previousState.copy()
                    }
                    is AddToQueueResult.Failure -> previousState.copy(error = result.fail)
                }
            }
        }
    }

}