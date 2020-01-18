package com.mobigod.emmusicplayer.rx

import io.reactivex.Scheduler

interface RxSchedulers {
    fun io(): Scheduler
    fun ui(): Scheduler
    fun computation(): Scheduler
}