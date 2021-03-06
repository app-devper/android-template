package com.devper.template.core.platform.fcm

import android.app.Application
import android.content.Intent
import android.os.Bundle
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.devper.template.BuildConfig

class LocalMessagingHelper(val context: Application) {

    fun sendNotification(bundle: Bundle) {
        val i = Intent(ACTION_BROADCAST)
        i.putExtras(bundle)
        LocalBroadcastManager.getInstance(context).sendBroadcast(i)
    }

    fun setApplicationForeground(foreground: Boolean) {
        mIsForeground = foreground
    }

    companion object {
        private const val PACKAGE_NAME = BuildConfig.APPLICATION_ID
        private var mIsForeground = false //this is a hack
        const val ACTION_BROADCAST = "$PACKAGE_NAME.push_notification"
    }
}
