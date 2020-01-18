package com.mobigod.emmusicplayer.base

import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.mobigod.emmusicplayer.utils.toastWith
import java.util.*


/**
 * Use this class when the activity needs to request permission from user
 */
abstract class PermissionActivity: BaseActivity() {
    private var requestCodes = Stack<Int>()


    private fun isPermissionGranted(permission: String): Boolean =
        ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED


    protected fun askForPermission(permission: String, requestId: Int) {
        requestCodes.push(requestId)

        if (!isPermissionGranted(permission)){

            ActivityCompat.requestPermissions(this,
                arrayOf(permission),
                requestId)
        }else{
            permissionGrantedByUser()
        }

    }

    /**
     * Called when permission is granted
     */
    abstract fun permissionGrantedByUser()


    override fun onRequestPermissionsResult (
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray) {

        when(requestCode) {
            requestCodes.pop() -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    permissionGrantedByUser()
                } else {
                    toastWith("Accept the permission to proceed")
                    finishAffinity()
                }
                return
            }
        }
    }

}