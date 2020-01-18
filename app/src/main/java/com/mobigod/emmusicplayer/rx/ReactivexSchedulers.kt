package com.mobigod.emmusicplayer.rx

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ReactivexSchedulers: RxSchedulers {
    override fun io() = Schedulers.io()
    override fun ui() = AndroidSchedulers.mainThread()
    override fun computation() = Schedulers.computation()
}