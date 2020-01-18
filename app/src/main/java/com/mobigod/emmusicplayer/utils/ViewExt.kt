package com.mobigod.emmusicplayer.utils

import android.app.Activity
import android.view.View
import android.widget.Toast


var View.visible: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if(value) View.VISIBLE else View.INVISIBLE
    }

fun View.hide() {
    visibility = View.GONE
}


fun View.show() {
    visibility = View.VISIBLE
}


fun View.isShowing() = visibility == View.VISIBLE

fun Activity.toastWith(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}