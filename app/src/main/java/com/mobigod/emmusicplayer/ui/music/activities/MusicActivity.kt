package com.mobigod.emmusicplayer.ui.music.activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.mobigod.emmusicplayer.R
import com.mobigod.emmusicplayer.base.BaseActivity
import com.mobigod.emmusicplayer.base.PermissionActivity
import com.mobigod.emmusicplayer.databinding.ActivityMusicBinding
import com.mobigod.emmusicplayer.ui.music.adapters.MusicPagerAdapter
import com.mobigod.emmusicplayer.ui.music.fragments.AlbumsFragment
import com.mobigod.emmusicplayer.ui.music.fragments.SongsFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import kotlinx.android.synthetic.main.toolbar_layout.*
import java.security.Permission

class MusicActivity: PermissionActivity() {
    lateinit var binding: ActivityMusicBinding

    private val fragments: MutableList<Fragment> = mutableListOf()
    private val subscribtions = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_music)

        initFragments()

        setUpToolBarAndNavDrawer()
        askForPermission(Manifest.permission.READ_EXTERNAL_STORAGE,2)

    }

    private fun initFragments() {
        val songFragment = SongsFragment()
        val albumsFragment = AlbumsFragment()

        subscribtions += songFragment.songSelected.subscribe {
            song ->
            //start song activity
            MusicPlayerActivity.start(this, song)
        }

        fragments.apply {
            add(songFragment)
            add(albumsFragment)
        }

    }

    override fun permissionGrantedByUser() {
        setupViewPagerAndTabs()
    }


    private fun setUpToolBarAndNavDrawer() {
        toolbar.title = "EM Music Player"

        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this, binding.drawerLayout,
            toolbar,
            R.string.open_drawer, R.string.close_drawer
        )

        actionBarDrawerToggle.syncState()
    }


    private fun setupViewPagerAndTabs() {
        binding.viewPager.adapter = MusicPagerAdapter(this, fragments)

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Songs"
                1 -> "Albums"
                else -> ""
            }
        }.attach()
    }



    companion object {
        const val SONG_ARG = "song_arg1"

        fun start(context: Context) {
            Intent(context, MusicActivity::class.java).also {
                context.startActivity(it)
            }
        }
    }
}