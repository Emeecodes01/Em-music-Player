package com.mobigod.emmusicplayer.ui.music.fragments

import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobigod.emmusicplayer.base.BaseFragmant
import com.mobigod.emmusicplayer.data.model.Song
import com.mobigod.emmusicplayer.databinding.SongListLayoutBinding
import com.mobigod.emmusicplayer.mvibase.MviView
import com.mobigod.emmusicplayer.ui.music.adapters.SongsAdapter
import com.mobigod.emmusicplayer.ui.music.intents.SongsFragmentIntent
import com.mobigod.emmusicplayer.ui.music.intents.SongsFragmentIntent.AddToQueueIntent
import com.mobigod.emmusicplayer.ui.music.intents.SongsFragmentIntent.LoadAllSongsFromStorageIntent
import com.mobigod.emmusicplayer.ui.music.states.SongsFragmentState
import com.mobigod.emmusicplayer.ui.music.viewmodel.SongsFragmentViewModel
import com.mobigod.emmusicplayer.utils.hide
import com.mobigod.emmusicplayer.utils.show
import com.mobigod.emmusicplayer.utils.visible
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class SongsFragment: BaseFragmant(), MviView<SongsFragmentIntent, SongsFragmentState>, SongsAdapter.SongSelectedInterface{

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var binding: SongListLayoutBinding
    private val subscriptions = CompositeDisposable()
    lateinit var viewModel: SongsFragmentViewModel
    private val addToQueuePublisherIntent = PublishSubject.create<AddToQueueIntent>()
    private val songsAdapter = SongsAdapter(this)

    private val publishSubject = PublishSubject.create<Song>()

    val songSelected: Observable<Song>
        get() = publishSubject


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = SongListLayoutBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(SongsFragmentViewModel::class.java)

        setUpViews()
        setUpListeners()
    }

    private fun setUpListeners() {
        //todo: implement listeners here
    }


    override fun onSongSelected(song: Song) {
        publishSubject.onNext(song)
    }
    private fun setUpViews() {

        binding.songsRv.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = songsAdapter
        }
    }

    override fun onStart() {
        super.onStart()
        bindToMVICircle()
    }


    private fun bindToMVICircle() {
        subscriptions +=
            viewModel.states().subscribe(this::render)
        viewModel.processIntents(intents())
    }


    override fun onDestroy() {
        super.onDestroy()
        subscriptions.dispose()
    }


    override fun intents(): Observable<SongsFragmentIntent> {
        return Observable.merge(
            loadIntent(),
            addToQueueIntent()
        )
    }


    override fun render(state: SongsFragmentState) {
        binding.progress.visible = state.loading

        if (state.songs.isEmpty())
            binding.songsRv.hide()
        else {
            binding.songsRv.show()
            songsAdapter.addSongs(state.songs)
        }

        if (state.error != null) {
            binding.songsRv.hide()
            binding.failureTv.show()
            binding.failureTv.text = state.error.message
        }
    }

    private fun loadIntent(): Observable<LoadAllSongsFromStorageIntent>{
        return Observable.just(LoadAllSongsFromStorageIntent)
    }


    private fun addToQueueIntent() = addToQueuePublisherIntent

}