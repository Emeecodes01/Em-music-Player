package com.mobigod.emmusicplayer.utils

import android.text.TextUtils
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


}