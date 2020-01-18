package com.mobigod.emmusicplayer.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView

class SongTitleTextView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0):
    TextView(context, attributeSet, defStyleAttr, defStyleRes) {

    override fun setText(text: CharSequence?, type: BufferType?) {
        super.setText(text, type)
    }
}