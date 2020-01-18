package com.mobigod.emmusicplayer.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.mobigod.emmusicplayer.R
import com.mobigod.emmusicplayer.base.BaseActivity
import com.mobigod.emmusicplayer.ui.music.activities.MusicActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import javax.inject.Inject
import javax.inject.Named

class SplashScreen : BaseActivity() {

    @Inject
    @Named("app_name")
    lateinit var appName: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        text.text = appName

        Handler().postDelayed({
            MusicActivity.start(this)
            finish()
        }, 2000)
    }


}
