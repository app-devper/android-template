package com.devper.template.data.device

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings.Secure
import android.telephony.TelephonyManager
import java.io.UnsupportedEncodingException
import java.util.*

class DeviceUuidFactory @SuppressLint("MissingPermission", "HardwareIds")
constructor(context: Context) {

    val deviceUuid: UUID?
        get() = uuid

    init {
        if (uuid == null) {
            synchronized(DeviceUuidFactory::class.java) {
                if (uuid == null) {
                    val prefs = context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE)
                    val id = prefs.getString(PREFS_DEVICE_ID, null)

                    if (id != null) {
                        // Use the ids previously computed and stored in the prefs file
                        uuid = UUID.fromString(id)
                    } else {
                        val androidId = Secure.getString(context.contentResolver, Secure.ANDROID_ID)
                        uuid = try {
                            if ("9774d56d682e549c" != androidId) {
                                UUID.nameUUIDFromBytes(androidId.toByteArray(charset("utf8")))
                            } else {
                                val deviceId = (context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager).deviceId
                                if (deviceId != null) UUID.nameUUIDFromBytes(deviceId.toByteArray(charset("utf8"))) else UUID.randomUUID()
                            }
                        } catch (e: UnsupportedEncodingException) {
                            UUID.randomUUID()
                        }
                        // Write the value out to the prefs file
                        prefs.edit().putString(PREFS_DEVICE_ID, uuid.toString()).apply()
                    }
                }
            }
        }
    }

    companion object {
        private const val PREFS_FILE = "device_id"
        private const val PREFS_DEVICE_ID = "device_id"
        private var uuid: UUID? = null
    }
}