package com.mobigod.emmusicplayer.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Song (
    val artistId: Long = 0,
    val artist: String = "",
    val title: String = "",
    val path: String = "",
    val displayName: String = "",
    val duration: Long = 0,
    val albumId: Long = 0,
    val album: String = "",
    var albumArt: String? = ""
): Parcelable