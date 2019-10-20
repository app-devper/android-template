package com.devper.template.data.remote

import com.devper.template.data.preference.AppPreference
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class AppInterceptor(private val pref: AppPreference) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()

        val newRequest = originalRequest.newBuilder().run {
            addHeader("Content-Type", "application/json")
            if (pref.token.isNotEmpty()) {
                addHeader("Authorization", "Bearer ${pref.token}")
            }
            addHeader("x-transaction-id", genTransactionId())
            build()
        }

        return chain.proceed(newRequest)
    }

    private fun randomAlphaNumeric(count: Int): String {
        var c = count
        val builder = StringBuilder()
        while (c-- != 0) {
            val character = (Math.random() * ALPHA_NUMERIC_STRING.length).toInt()
            builder.append(ALPHA_NUMERIC_STRING[character])
        }
        return builder.toString()
    }

    private fun genTransactionId(): String {
        val format = SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.getDefault())
        format.format(Date())
        return format.format(Date()) + "_" + randomAlphaNumeric(5)
    }

    companion object {
        private const val ALPHA_NUMERIC_STRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"
    }

}