package com.devper.template.core.platform.fcm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.messaging.FirebaseMessaging
import timber.log.Timber

class MessagingHandler(private val context: Context, lifecycle: Lifecycle) : LifecycleObserver {

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
        LocalBroadcastManager.getInstance(context).registerReceiver(messagingReceiver, filter)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun stop() {
        LocalBroadcastManager.getInstance(context).unregisterReceiver(messagingReceiver)
    }

    fun subscribeToTopic(topic: String) {
        FirebaseMessaging.getInstance().subscribeToTopic(topic)
            .addOnCompleteListener { task ->
                var msg = "Subscribe topic success"
                if (!task.isSuccessful) {
                    msg = "Subscribe topic fail"
                }
                Timber.d("subscribeToTopic: $msg")
            }
    }

    inner class MessagingReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val bundle = intent.extras ?: return
            Timber.d("MessagingHandler: $bundle")
            if (bundle.getString("body") == null) {
                return
            }
            onMessage(bundle)
        }
    }
}
