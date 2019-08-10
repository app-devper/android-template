package com.devper.template

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.provider.Settings
import androidx.multidex.MultiDexApplication
import com.devper.fcm.LocalMessagingHelper
import com.devper.template.common.appModules
import com.devper.template.common.pref.AppPreference
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber
import java.util.*

class TemplateApplication : MultiDexApplication(), Application.ActivityLifecycleCallbacks {

    private lateinit var localMessagingHelper: LocalMessagingHelper
    private val pref: AppPreference by inject()

    override fun onCreate() {
        super.onCreate()

        localMessagingHelper = LocalMessagingHelper(this)

        startKoin {
            androidLogger()
            androidContext(applicationContext)
            modules(appModules)
        }

        registerActivityLifecycleCallbacks(this)
        verifyUUID()
    }

    override fun onActivityCreated(activity: Activity, bundle: Bundle?) {

    }

    override fun onActivityStarted(activity: Activity) {
        localMessagingHelper.setApplicationForeground(true)
    }

    override fun onActivityResumed(activity: Activity) {
        localMessagingHelper.setApplicationForeground(true)
        //localMessagingHelper.setCurrentClass(activity.javaClass)
    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {
        localMessagingHelper.setApplicationForeground(false)
    }

    override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle?) {

    }

    override fun onActivityDestroyed(activity: Activity) {
        localMessagingHelper.setApplicationForeground(false)
        // localMessagingHelper.setCurrentClass(null)
    }

    @SuppressLint("HardwareIds")
    private fun verifyUUID() {
        var androidId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        if ("9774d56d682e549c" == androidId) {
            androidId = UUID.randomUUID().toString().replace("-", "")
        }
        if (pref.deviceUuid.isEmpty()) {
            pref.deviceUuid = androidId
        }
        Timber.i("Device UUID: %s", pref.deviceUuid)

        if (pref.appUuid.isEmpty()) {
            pref.appUuid = UUID.randomUUID().toString()
        }
        Timber.i("TemplateApplication UUID: %s", pref.appUuid)
    }

}
