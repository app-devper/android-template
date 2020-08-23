package com.devper.template.core.platform.fcm

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONException

@SuppressLint("LogNotTimber")
class MessagingService : FirebaseMessagingService() {


    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "Refreshed token: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.from)
        remoteMessage.let {
            if (it.data.isNotEmpty()) {
                Log.d(TAG, "Message data payload: " + remoteMessage.data)
            }
            if (it.notification != null) {
                Log.d(TAG, "Message Notification Body: " + remoteMessage.notification?.body)
            }
            buildLocalNotification(it)
        }
    }

    private fun buildLocalNotification(remoteMessage: RemoteMessage) {
        val badgeHelper = BadgeHelper(this)
        val data = remoteMessage.data
        val notification = remoteMessage.notification
        val bundle = Bundle()
        notification?.notificationCount?.let {
            bundle.putInt("badge", it)
            badgeHelper.badgeCount = it
        }
        for (entry in data.entries) {
            bundle.putString(entry.key, entry.value)
        }
        try {
            val helper = LocalMessagingHelper(this.application)
            helper.sendNotification(bundle)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    companion object {
        private val TAG = MessagingService::class.java.simpleName
    }
}
