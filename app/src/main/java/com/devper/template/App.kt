package com.devper.template

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.devper.fcm.LocalMessagingHelper
import com.devper.template.di.appModule
import com.devper.template.domain.domainsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application(), LifecycleObserver {

    private lateinit var localMessagingHelper: LocalMessagingHelper

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        localMessagingHelper = LocalMessagingHelper(this)

        startKoin {
            androidLogger()
            androidContext(applicationContext)
            modules(domainsModule)
            modules(appModule)
        }

        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {
        Timber.d("App in background")
        localMessagingHelper.setApplicationForeground(false)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {
        Timber.d("App in foreground")
        localMessagingHelper.setApplicationForeground(true)
    }

}
