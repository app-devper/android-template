package com.devper.fcm

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class MessagingHandler(private val mActivity: Activity, lifecycle: Lifecycle) : LifecycleObserver {

    private var myReceiver: MessagingReceiver
    var message: MutableLiveData<Bundle> = MutableLiveData()
    var token: MutableLiveData<String> = MutableLiveData()

    init {
        lifecycle.addObserver(this)
        myReceiver = MessagingReceiver()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun start() {
        val filter = IntentFilter()
        filter.addAction(LocalMessagingHelper.ACTION_BROADCAST)
        filter.addAction(MessagingService.ACTION_TOKEN_BROADCAST)
        LocalBroadcastManager.getInstance(mActivity).registerReceiver(myReceiver, filter)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun stop() {
        LocalBroadcastManager.getInstance(mActivity).unregisterReceiver(myReceiver)
    }

    companion object {
        private val TAG = MessagingHandler::class.java.simpleName
    }

    inner class MessagingReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val bundle = intent.extras ?: return
            Log.d(TAG, "MessagingHandler: $bundle")
            val fbToken = bundle.getString(MessagingService.EXTRA_FCM_TOKEN)
            fbToken?.let {
                token.postValue(it)
            }
            if (bundle.getString("body") == null) {
                return
            }
            message.postValue(bundle)
        }
    }
}
