package com.mobigod.emmusicplayer.ui.music.fragments.musicviews

/**
 * All Fragments that transmit player controls must implement this interface
 */
interface PlayerView {
    fun nextBtnListener()
    fun previousBtnListener()
    fun playBtnListener()
    fun pauseBtnListener()
    fun repeatBtnListener()
    fun shuffleBtnClicked()
}