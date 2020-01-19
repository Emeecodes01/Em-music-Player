package com.mobigod.emmusicplayer.ui.music.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.mobigod.emmusicplayer.ui.music.fragments.AlbumsFragment
import com.mobigod.emmusicplayer.ui.music.fragments.SongsFragment

class MusicPagerAdapter(fragmentActivity: FragmentActivity, private val frags: List<Fragment>): FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount() = frags.size

    override fun createFragment(position: Int) =
        when(position) {
            0 -> frags[position]
            else -> frags[position]
        }
}