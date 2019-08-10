package com.devper.common.helper

import android.content.Context
import android.net.ConnectivityManager

class NetworkInfoHelper private constructor(context: Context) {

    private var connectivityManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private var connected = false

    val isOnline: Boolean
        get() {
            try {
                val networkInfo = connectivityManager.activeNetworkInfo
                connected = networkInfo != null && networkInfo.isAvailable && networkInfo.isConnected
                return connected
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return connected
        }

    companion object {

        private var instance: NetworkInfoHelper? = null

        fun getInstance(ctx: Context): NetworkInfoHelper {
            if (instance == null) {
                instance = NetworkInfoHelper(ctx)
            }
            return instance!!
        }
    }
}
