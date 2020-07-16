package com.devper.template.core.platform.fcm

import android.util.Log
import com.devper.template.BuildConfig
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONException
import org.json.JSONObject

class MessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "Refreshed token: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        Log.d(TAG, "From: " + remoteMessage.from)

        remoteMessage.let {
            // Check if message contains a data payload.
            if (it.data.isNotEmpty()) {
                Log.d(TAG, "Message data payload: " + remoteMessage.data)
            }

            // Check if message contains a notification payload.
            if (it.notification != null) {
                Log.d(TAG, "Message Notification Body: " + remoteMessage.notification?.body)
            }

            handleBadge(it)
            buildLocalNotification(it)
        }

    }

    private fun handleBadge(remoteMessage: RemoteMessage) {
        val badgeHelper = BadgeHelper(this)

        val data = remoteMessage.data
        if (data["badge"] == null) {
            return
        }

        try {
            val badgeCount = Integer.parseInt(data["badge"] as String)
            badgeHelper.badgeCount = badgeCount
        } catch (e: Exception) {
            Log.e(TAG, "Badge count needs to be an integer", e)
        }
    }

    private fun buildLocalNotification(remoteMessage: RemoteMessage) {
        val data = remoteMessage.data
        val customNotification = data["custom_notification"]
        if (customNotification != null) {
            try {
                val bundle = BundleJSONConverter.convertToBundle(JSONObject(customNotification))
                val helper = LocalMessagingHelper(this.application)
                helper.sendNotification(bundle)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        private val TAG = MessagingService::class.java.simpleName
    }
}
