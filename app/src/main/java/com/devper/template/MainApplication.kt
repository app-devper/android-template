package com.devper.template

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.devper.template.core.platform.fcm.LocalMessagingHelper
import com.devper.template.core.platform.session.CountDownSession
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : Application(), LifecycleObserver {

    private lateinit var localMessagingHelper: LocalMessagingHelper
    @Inject lateinit var countDownSession: CountDownSession

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        localMessagingHelper = LocalMessagingHelper(this)

//        startKoin {
//            androidLogger()
//            androidContext(applicationContext)
//            modules(domainsModule)
//            modules(appModule)
//        }

        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {
        Timber.d("App in background")
        countDownSession.isForeground = false
        localMessagingHelper.setApplicationForeground(false)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {
        Timber.d("App in foreground")
        countDownSession.isForeground = true
        localMessagingHelper.setApplicationForeground(true)
    }

}
