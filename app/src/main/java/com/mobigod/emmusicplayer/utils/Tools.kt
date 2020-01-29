package com.mobigod.emmusicplayer.utils


import android.content.Context
import android.net.Uri
import android.text.TextUtils
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern
import android.provider.MediaStore
import android.graphics.Bitmap
import android.graphics.ImageDecoder


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


    fun getBitmapFromPath(context: Context, path: String)
        = MediaStore.Images.Media.getBitmap(context.contentResolver, Uri.parse(path))

}