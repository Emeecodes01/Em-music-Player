package com.mobigod.emmusicplayer.ui.music.activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayoutMediator
import com.mobigod.emmusicplayer.R
import com.mobigod.emmusicplayer.base.BaseActivity
import com.mobigod.emmusicplayer.base.PermissionActivity
import com.mobigod.emmusicplayer.databinding.ActivityMusicBinding
import com.mobigod.emmusicplayer.ui.music.adapters.MusicPagerAdapter
import kotlinx.android.synthetic.main.toolbar_layout.*
import java.security.Permission

class MusicActivity: PermissionActivity() {

    lateinit var binding: ActivityMusicBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_music)

        askForPermission(Manifest.permission.READ_EXTERNAL_STORAGE,2)

    }

    override fun permissionGrantedByUser() {
        setUpToolBarAndNavDrawer()
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
        binding.viewPager.adapter = MusicPagerAdapter(this)

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Songs"
                1 -> "Albums"
                else -> ""
            }
        }.attach()
    }



    companion object {
        fun start(context: Context) {
            Intent(context, MusicActivity::class.java).also {
                context.startActivity(it)
            }
        }
    }
}