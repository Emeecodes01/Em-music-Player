package com.mobigod.emmusicplayer.ui.music.fragments.musicviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mobigod.emmusicplayer.data.model.Song
import com.mobigod.emmusicplayer.databinding.CircularPlayerViewBinding
import com.mobigod.emmusicplayer.utils.Tools

const val SONG_FRAGMENT_ARG = "song-fragment-arg"

class CircularMusicViewFragment: Fragment() {
    private var song: Song? = null

    private lateinit var binding: CircularPlayerViewBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = CircularPlayerViewBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        song = arguments?.getParcelable(SONG_FRAGMENT_ARG)
        setUpPlayerViews()
    }

    private fun setUpPlayerViews() {
        binding.songTitleTv.text = song?.title
        binding.artistTv.text = song?.artist
        binding.durationTv.text = Tools.convertMilliToDuration(song?.duration)
        binding.progressTv.text = "0:00"
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