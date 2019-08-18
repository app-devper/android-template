package com.devper.fcm

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class LocalMessagingHelper(context: Application) {

    private val mContext: Context

    private val mainActivityClass: Class<*>?
        get() {
            val packageName = mContext.packageName
            val launchIntent = mContext.packageManager.getLaunchIntentForPackage(packageName)
            val className = launchIntent?.component?.className
            return try {
                Class.forName(className!!)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

    init {
        mContext = context
    }

    fun sendNotification(bundle: Bundle) {
        try {
            var intentClass = mainActivityClass ?: return

            if (currentClass != null) {
                intentClass = currentClass as Class<*>
            }

            if (bundle.getString("body") == null) {
                return
            }

            val res = mContext.resources
            val packageName = mContext.packageName

            var title = bundle.getString("title")
            if (title == null) {
                val appInfo = mContext.applicationInfo
                title = mContext.packageManager.getApplicationLabel(appInfo).toString()
            }

            val notification = NotificationCompat.Builder(mContext, CHANNEL_ID)
                    .setContentTitle(title)
                    .setContentText(bundle.getString("body"))
                    .setTicker(bundle.getString("ticker"))
                    .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
                    .setAutoCancel(bundle.getBoolean("auto_cancel", true))
                    .setNumber(bundle.getInt("number"))
                    .setSubText(bundle.getString("sub_text"))
                    .setGroup(bundle.getString("group"))
                    .setVibrate(longArrayOf(0, DEFAULT_VIBRATION))
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setExtras(bundle.getBundle("data"))

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notification.setChannelId(CHANNEL_ID)
            }

            //priority
            when (bundle.getString("priority", "")) {
                "min" -> notification.priority = NotificationCompat.PRIORITY_MIN
                "high" -> notification.priority = NotificationCompat.PRIORITY_HIGH
                "max" -> notification.priority = NotificationCompat.PRIORITY_MAX
                else -> notification.priority = NotificationCompat.PRIORITY_DEFAULT
            }

            //icon
            val smallIcon = bundle.getString("icon", "ic_launcher")
            val smallIconResId = res.getIdentifier(smallIcon, "mipmap", packageName)
            notification.setSmallIcon(smallIconResId)

            //large icon
            val largeIcon = bundle.getString("large-icon")
            if (largeIcon != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (largeIcon.startsWith("http://") || largeIcon.startsWith("https://")) {
                    val bitmap = getBitmapFromURL(largeIcon)
                    notification.setLargeIcon(bitmap)
                } else {
                    val largeIconResId = res.getIdentifier(largeIcon, "mipmap", packageName)
                    val largeIconBitmap = BitmapFactory.decodeResource(res, largeIconResId)

                    if (largeIconResId != 0) {
                        notification.setLargeIcon(largeIconBitmap)
                    }
                }
            }

            //big text
            val bigText = bundle.getString("big_text")
            if (bigText != null) {
                notification.setStyle(NotificationCompat.BigTextStyle().bigText(bigText))
            }

            //sound
            var soundName = bundle.getString("sound", "default")
            if (!soundName.equals("default", ignoreCase = true)) {
                var soundResourceId = res.getIdentifier(soundName, "raw", packageName)
                if (soundResourceId == 0) {
                    soundName = soundName.substring(0, soundName.lastIndexOf('.'))
                    soundResourceId = res.getIdentifier(soundName, "raw", packageName)
                }
                notification.setSound(Uri.parse("android.resource://$packageName/$soundResourceId"))
            }

            //color
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                notification.setCategory(NotificationCompat.CATEGORY_CALL)

                val color = bundle.getString("color")
                if (color != null) {
                    notification.color = Color.parseColor(color)
                }
            }

            //vibrate
            if (bundle.containsKey("vibrate")) {
                val vibrate = bundle.getLong("vibrate", Math.round(bundle.getDouble("vibrate", bundle.getInt("vibrate").toDouble())))
                if (vibrate > 0) {
                    notification.setVibrate(longArrayOf(0, vibrate))
                } else {
                    notification.setVibrate(null)
                }
            }

            //lights
            if (bundle.getBoolean("lights")) {
                notification.setDefaults(NotificationCompat.DEFAULT_LIGHTS)
            }

            Log.d(TAG, "broadcast intent before showing notification")
            val i = Intent(ACTION_BROADCAST)
            i.putExtras(bundle)
            LocalBroadcastManager.getInstance(mContext).sendBroadcast(i)

            if (!mIsForeground || bundle.getBoolean("show_in_foreground",false)) {
                val intent = Intent(mContext, intentClass)
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                intent.putExtras(bundle)
                intent.action = bundle.getString("click_action")

                val notificationID = if (bundle.containsKey("_id")) bundle.getString("_id", "").hashCode() else System.currentTimeMillis().toInt()
                val pendingIntent = PendingIntent.getActivity(mContext, notificationID, intent, PendingIntent.FLAG_UPDATE_CURRENT)

                val notificationManager = mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                // Android O requires a Notification Channel.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    // Create the channel for the notification
                    val mChannel = NotificationChannel(CHANNEL_ID, title, NotificationManager.IMPORTANCE_DEFAULT)
                    // Set the Notification Channel for the Notification Manager.
                    notificationManager.createNotificationChannel(mChannel)
                }

                notification.setContentIntent(pendingIntent)

                val info = notification.build()

                if (bundle.containsKey("tag")) {
                    val tag = bundle.getString("tag")
                    notificationManager.notify(tag, notificationID, info)
                } else {
                    notificationManager.notify(notificationID, info)
                }
            }

        } catch (e: Exception) {
            Log.e(TAG, "failed to send local notification", e)
        }
    }

    fun setApplicationForeground(foreground: Boolean) {
        mIsForeground = foreground
    }

    private fun getBitmapFromURL(strURL: String): Bitmap? {
        return try {
            val url = URL(strURL)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    companion object {
        private const val DEFAULT_VIBRATION = 300L
        private val TAG = LocalMessagingHelper::class.java.simpleName
        private const val PACKAGE_NAME = BuildConfig.APPLICATION_ID
        private var mIsForeground = false //this is a hack
        private var currentClass: Class<*>? = null
        private const val CHANNEL_ID = "channel_02"

        const val ACTION_BROADCAST = "$PACKAGE_NAME.push_notification"
    }
}
