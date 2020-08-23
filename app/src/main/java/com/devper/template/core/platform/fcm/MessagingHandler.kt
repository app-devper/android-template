package com.devper.template.core.platform.fcm

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.*
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.devper.template.domain.model.notification.Notification
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import timber.log.Timber

class MessagingHandler(private val mActivity: Activity, lifecycle: Lifecycle) : LifecycleObserver {

    private var messagingReceiver: MessagingReceiver

    var onMessage: (Bundle: Bundle) -> Unit = {}

    init {
        lifecycle.addObserver(this)
        messagingReceiver = MessagingReceiver()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun start() {
        val filter = IntentFilter()
        filter.addAction(LocalMessagingHelper.ACTION_BROADCAST)
        LocalBroadcastManager.getInstance(mActivity).registerReceiver(messagingReceiver, filter)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun stop() {
        LocalBroadcastManager.getInstance(mActivity).unregisterReceiver(messagingReceiver)
    }

    fun subscribeToTopic(topic: String) {
        FirebaseMessaging.getInstance().subscribeToTopic(topic)
            .addOnCompleteListener { task ->
                var msg = "Subscribe topic success"
                if (!task.isSuccessful) {
                    msg = "Subscribe topic fail"
                }
            }
    }

    companion object {
        private val TAG = MessagingHandler::class.java.simpleName
    }

    inner class MessagingReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val bundle = intent.extras ?: return
            Log.d(TAG, "MessagingHandler: $bundle")
            if (bundle.getString("body") == null) {
                return
            }
            onMessage(bundle)
        }
    }
}
