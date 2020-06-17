package com.devper.template.data.device

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context.TELEPHONY_SERVICE
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import com.devper.template.domain.provider.AppInfoProvider
import java.util.*

class AndroidAppInfo(private val application: Application) : AppInfoProvider {

    override val imsi: String
        @SuppressLint("HardwareIds", "MissingPermission")
        get() {
            val telephonyManager = application.getSystemService(TELEPHONY_SERVICE) as TelephonyManager
            return telephonyManager.subscriberId
        }

    override val uuid: String
        get() {
            return DeviceUuidFactory(application).deviceUuid.toString()
        }

    override val androidId: String
        @SuppressLint("HardwareIds")
        get() {
            var androidId = Settings.Secure.getString(application.contentResolver, Settings.Secure.ANDROID_ID)
            if ("9774d56d682e549c" == androidId) {
                androidId = UUID.randomUUID().toString().replace("-", "")
            }
            return androidId
        }

    override val apiLevel: String
        get() = Build.VERSION.SDK_INT.toString()

    override val board: String
        get() = Build.BOARD

    override val bootLoader: String
        get() = Build.BOOTLOADER

    override val brand: String
        get() = Build.BRAND

    override val buildId: String
        get() = Build.ID

    override val buildTime: String
        get() = Build.TIME.toString()

    override val fingerprint: String
        get() = Build.FINGERPRINT

    override val hardware: String
        get() = Build.HARDWARE

    override val host: String
        get() = Build.HOST

    override val model: String
        get() = Build.MODEL

    override val serialNo: String
        @SuppressLint("MissingPermission")
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Build.getSerial()
        } else {
            Build.SERIAL
        }

    override val user: String
        get() = Build.MODEL

    override val appId = application.packageName ?: ""

    override val name: String = application.packageName ?: ""

    override val imei: String
        @SuppressLint("HardwareIds", "MissingPermission")
        get() {
            val telephonyManager = application.getSystemService(TELEPHONY_SERVICE) as TelephonyManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if (telephonyManager.phoneCount == 2) {
                        when (telephonyManager.phoneType) {
                            TelephonyManager.PHONE_TYPE_CDMA -> telephonyManager.getMeid(0)
                            TelephonyManager.PHONE_TYPE_GSM -> telephonyManager.getImei(0)
                            else -> ""
                        }
                    } else {
                        when (telephonyManager.phoneType) {
                            TelephonyManager.PHONE_TYPE_CDMA -> telephonyManager.meid
                            TelephonyManager.PHONE_TYPE_GSM -> telephonyManager.imei
                            else -> ""
                        }
                    }
                } else {
                    if (telephonyManager.phoneCount == 2) {
                        telephonyManager.getDeviceId(0)
                    } else {
                        telephonyManager.deviceId
                    }
                }
            } else {
                return telephonyManager.deviceId
            }
        }

    override val screenDensity: String
        get() = application.resources.displayMetrics.densityDpi.toString()

    override val screenResolution: String
        get() {
            return application.resources.displayMetrics.heightPixels.toString() + "x" + application.resources.displayMetrics.widthPixels.toString()
        }

}