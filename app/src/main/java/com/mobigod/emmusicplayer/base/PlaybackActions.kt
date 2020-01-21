package com.mobigod.emmusicplayer.base

interface PlaybackActions {
    fun handlePlayPauseClicked()
    fun handleNextClicked()
    fun handlePreviousClicked()
    /**
     * @param times -> numbers of time clicked
     */
    fun handleShuffleClicked(times: Int)
    /**
     * @param times -> numbers of time clicked
     */
    fun handleRepeatClicked(times: Int)
}