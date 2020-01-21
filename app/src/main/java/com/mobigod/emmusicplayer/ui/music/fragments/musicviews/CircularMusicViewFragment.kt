package com.mobigod.emmusicplayer.ui.music.fragments.musicviews

import android.os.Bundle
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.changes
import com.jakewharton.rxbinding3.widget.systemChanges
import com.jakewharton.rxbinding3.widget.userChanges
import com.mobigod.emmusicplayer.base.BasePlaybackView
import com.mobigod.emmusicplayer.data.model.Song
import com.mobigod.emmusicplayer.databinding.CircularPlayerViewBinding
import com.mobigod.emmusicplayer.utils.Tools
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import kotlin.math.roundToInt
import kotlin.math.roundToLong

const val SONG_FRAGMENT_ARG = "song-fragment-arg"

class CircularMusicViewFragment: BasePlaybackView() {
    private var song: Song? = null
    private lateinit var binding: CircularPlayerViewBinding
    private val subs = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = CircularPlayerViewBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        song = arguments?.getParcelable(SONG_FRAGMENT_ARG)
        setUpPlayerViews()
        setUpListeners()
    }


    private fun setUpListeners() {
        subs += binding.progress.changes().subscribeBy (
            onNext = {
                    percentage ->
                val progress = (percentage * 0.01 * song!!.duration).roundToLong()
                binding.progressTv.text = Tools.convertMilliToDuration(progress)
            },

            onComplete = {
                println("Completed..")
            }
        )

        subs += binding.playPauseBtn.clicks()
            .subscribe {
                handlePlayPauseClicked()
            }

        subs += binding.nextBtn.clicks()
            .subscribe {
                handleNextClicked()
            }

        subs += binding.previousBtn.clicks()
            .subscribe {
                handlePreviousClicked()
            }

        subs += binding.shuffleBtn.clicks()
            .subscribe {
                handleShuffleClicked(noOfShuffleClicks++)
            }

        subs += binding.repeatBtn.clicks()
            .subscribe {
                handleRepeatClicked(noOfRepeatClicks)
            }
    }

    private fun setUpPlayerViews() {
        binding.songTitleTv.text = song?.title
        binding.artistTv.text = song?.artist
        binding.durationTv.text = Tools.convertMilliToDuration(song?.duration)
        binding.progressTv.text = "0:00"
    }


    override fun syncMetaData(metadataCompat: MediaMetadataCompat?) {
        mediaController?.queue
    }

    override fun syncPlaybackState(playbackStateCompat: PlaybackStateCompat?) {

    }

    override fun onStop() {
        super.onStop()
        MediaControllerCompat.getMediaController(activity!!)?.unregisterCallback(controllerCallback)
    }


    companion object {

        fun getInstance(song: Song?)
         = CircularMusicViewFragment().apply {
            arguments = Bundle().apply {
                putParcelable(SONG_FRAGMENT_ARG, song)
            }
        }
    }
}

