package com.views.ldebugview

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.utils.util.ServiceUtils
import com.views.ldebugview.LComunicateDebug.postFromActivity

object LDebug {
    private const val CODE = 1993

    @JvmStatic
    fun init(activity: Activity) {
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.parse("package:" + activity.packageName)
        )
        activity.startActivityForResult(intent, CODE)
    }

    @JvmStatic
    fun checkPermission(activity: Activity, requestCode: Int, resultCode: Int) {
        if (requestCode == CODE) {
            init(activity)
        }
    }

    @JvmStatic
    fun stop() {
        ServiceUtils.stopService(LDebugViewService::class.java.name)
    }

    fun log(log: String?) {
        postFromActivity(
            msg = LComunicateDebug.MsgFromActivity(
                type = LComunicateDebug.MsgFromActivity.TYPE_D,
                msg = log,
                any = null
            )
        )
    }

    fun log(type: Int, log: String?) {
        postFromActivity(
            msg = LComunicateDebug.MsgFromActivity(
                type = type,
                msg = log,
                any = null
            )
        )
    }

    fun log(o: Any?) {
        postFromActivity(
            msg = LComunicateDebug.MsgFromActivity(
                type = LComunicateDebug.MsgFromActivity.TYPE_D,
                msg = "",
                any = o
            )
        )
    }

    fun log(type: Int, o: Any?) {
        postFromActivity(
            msg = LComunicateDebug.MsgFromActivity(
                type = type,
                msg = "",
                any = o
            )
        )
    }
}
