package com.mobigod.emmusicplayer.utils


import android.text.TextUtils
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

object Tools {

    fun getSongNameFromRoughPath(roughPath: String): String {

        if (TextUtils.isEmpty(roughPath))
            return ""

        val string = removeExtension(roughPath)
        return string.replace(Regex("[0-9]"), "")
    }


    private fun removeExtension(path: String): String {
        val reversedStr = path.reversed()
        val str = reversedStr.substringAfter(".")
        return str.reversed()
    }


    fun convertMilliToDuration(millis: Long?): String{
        var duration = ""
        if (millis != null)
        duration =  String.format("%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(millis),
            TimeUnit.MILLISECONDS.toSeconds(millis) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        )
        return duration
    }


}