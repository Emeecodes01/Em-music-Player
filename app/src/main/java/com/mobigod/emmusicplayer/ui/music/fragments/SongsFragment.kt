package com.mobigod.emmusicplayer.ui.music.fragments

import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobigod.emmusicplayer.base.BaseFragmant
import com.mobigod.emmusicplayer.databinding.SongListLayoutBinding

class SongsFragment: BaseFragmant() {
    lateinit var binding: SongListLayoutBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = SongListLayoutBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}