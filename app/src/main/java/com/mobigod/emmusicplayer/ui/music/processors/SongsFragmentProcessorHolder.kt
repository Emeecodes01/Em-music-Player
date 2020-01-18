package com.mobigod.emmusicplayer.ui.music.processors

import com.mobigod.emmusicplayer.data.repo.Repository
import com.mobigod.emmusicplayer.rx.ReactivexSchedulers
import com.mobigod.emmusicplayer.ui.music.actions.SongsFragmentAction
import com.mobigod.emmusicplayer.ui.music.actions.SongsFragmentAction.AddToQueueAction
import com.mobigod.emmusicplayer.ui.music.actions.SongsFragmentAction.LoadSongsFromStorageAction
import com.mobigod.emmusicplayer.ui.music.results.SongsFragmentResult
import com.mobigod.emmusicplayer.ui.music.results.SongsFragmentResult.*
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import java.lang.IllegalStateException
import javax.inject.Inject

class SongsFragmentProcessorHolder @Inject constructor(repository: Repository, schedulers: ReactivexSchedulers) {

    private val loadAllSongsProcessor =
        ObservableTransformer<LoadSongsFromStorageAction, LoadSongsFromStorageResult>{actions ->
            actions.flatMap {
                repository.getAllSongsFromStorage()
                    .map {songs ->  LoadSongsFromStorageResult.Success(songs)}
                    .cast(LoadSongsFromStorageResult::class.java)
                    .onErrorReturn(LoadSongsFromStorageResult::Failure)
                    .subscribeOn(schedulers.io())
                    .observeOn(schedulers.ui())
                    .startWith(LoadSongsFromStorageResult.Loading)
            }
        }


    private val addToQueueProcessor =
        ObservableTransformer<AddToQueueAction, AddToQueueResult> {action ->
            action.flatMap {
                val song = it.song
                //for now, implement this later
                Observable.just("")
                    .map { item -> AddToQueueResult.Success }
                    .cast(AddToQueueResult::class.java)
                    .onErrorReturn(AddToQueueResult::Failure)
                    .subscribeOn(schedulers.io())
                    .observeOn(schedulers.ui())
            }
        }



    internal val actionProcessor =
        ObservableTransformer<SongsFragmentAction, SongsFragmentResult> { actions ->
            actions.publish { shared ->

                Observable.merge(
                    shared.ofType(LoadSongsFromStorageAction::class.java).compose(loadAllSongsProcessor),
                    shared.ofType(AddToQueueAction::class.java).compose(addToQueueProcessor))
                    .mergeWith(
                        shared.filter { v ->
                            v !is LoadSongsFromStorageAction && v !is AddToQueueAction

                        }.flatMap {
                            Observable.error<LoadSongsFromStorageResult>(IllegalStateException("Not a sub type"))
                        }
                    )
            }
        }


}