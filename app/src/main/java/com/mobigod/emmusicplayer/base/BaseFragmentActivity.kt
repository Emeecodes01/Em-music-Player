package com.mobigod.emmusicplayer.base

import android.os.Handler
import androidx.fragment.app.Fragment
import com.mobigod.emmusicplayer.R

abstract class BaseFragmentActivity: BaseActivity() {



    open fun startFragment(layoutId: Int, fragment: Fragment, tag: String?,
                           animIn: Int = R.anim.fade_in, animOut: Int = R.anim.fade_out) {
        val frag = supportFragmentManager.findFragmentByTag(tag)
        if (frag != null) {
            supportFragmentManager.popBackStackImmediate(tag, 0)
        } else {
            Handler().postDelayed({
                supportFragmentManager
                    .beginTransaction()
                    .setCustomAnimations(animIn, animOut)
                    .replace(layoutId, fragment, tag)
                    .addToBackStack(null)
                    .commit()
            }, 250)
        }
    }


    open fun popFragment() {
        supportFragmentManager.popBackStack()
    }



    open fun hasOneItemOnBackStack()
            = supportFragmentManager.backStackEntryCount == 1


}