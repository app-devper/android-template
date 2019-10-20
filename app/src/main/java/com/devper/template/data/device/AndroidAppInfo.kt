package com.devper.template.data.device

import android.Manifest.permission.READ_PHONE_STATE
import android.annotation.SuppressLint
import android.app.Application
import android.content.Context.TELEPHONY_SERVICE
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import androidx.core.app.ActivityCompat
import com.devper.template.BuildConfig
import com.devper.template.R
import com.devper.template.domain.model.application.AppInfo
import java.util.*

class AndroidAppInfo(private val application: Application) : AppInfo {

    override val imsi: String
        @SuppressLint("HardwareIds")
        get() {
            val telephonyManager = application.getSystemService(TELEPHONY_SERVICE) as TelephonyManager
            return if (ActivityCompat.checkSelfPermission(application, READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                ""
            } else {
                telephonyManager.subscriberId
            }
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
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (ActivityCompat.checkSelfPermission(application, READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                Build.SERIAL
            } else {
                Build.getSerial()
            }
        } else {
            Build.SERIAL
        }

    override val user: String
        get() = Build.MODEL

    override val appId = BuildConfig.APPLICATION_ID

    override val name: String = application.getString(R.string.app_name)

    override val imei: String
        @SuppressLint("HardwareIds")
        get() {
            val telephonyManager = application.getSystemService(TELEPHONY_SERVICE) as TelephonyManager
            if (ActivityCompat.checkSelfPermission(application, READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return ""
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if (telephonyManager.phoneCount == 2) {
                        when {
                            telephonyManager.phoneType == TelephonyManager.PHONE_TYPE_CDMA -> telephonyManager.getMeid(0)
                            telephonyManager.phoneType == TelephonyManager.PHONE_TYPE_GSM -> telephonyManager.getImei(0)
                            else -> ""
                        }
                    } else {
                        when {
                            telephonyManager.phoneType == TelephonyManager.PHONE_TYPE_CDMA -> telephonyManager.meid
                            telephonyManager.phoneType == TelephonyManager.PHONE_TYPE_GSM -> telephonyManager.imei
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