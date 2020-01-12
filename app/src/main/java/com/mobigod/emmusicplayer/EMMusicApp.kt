package com.mobigod.emmusicplayer

import android.app.Application
import com.mobigod.emmusicplayer.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class EMMusicApp: DaggerApplication() {


    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
            .application(this)
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        //instantiate other stuffs here

    }
}