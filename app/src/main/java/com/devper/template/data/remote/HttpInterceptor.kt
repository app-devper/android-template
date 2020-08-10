package com.devper.template.data.remote

import com.devper.template.core.exception.InternetException
import com.devper.template.core.platform.helper.NetworkInfoHelper
import com.devper.template.data.session.AppSessionProvider
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HttpInterceptor @Inject constructor (
    private val session: AppSessionProvider,
    private val networkInfoHelper: NetworkInfoHelper
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!networkInfoHelper.isOnline) {
            throw InternetException("No internet connection")
        } else {
            val originalRequest = chain.request()
            val newRequest = originalRequest.newBuilder().run {
                addHeader("Content-Type", "application/json")
                session.accessToken?.let {
                    if (it.isNotEmpty()) {
                        addHeader("Authorization", "Bearer $it")
                    }
                }
                addHeader("x-app-language", "th")
                addHeader("x-transaction-id", genTransactionId())
            }
            val tag = originalRequest.tag(Invocation::class.java)
            val annotation = tag?.method()?.getAnnotation(Timeout::class.java)
            return if (annotation == null) {
                chain.proceed(newRequest.build())
            } else {
                chain.withConnectTimeout(annotation.seconds, TimeUnit.SECONDS)
                    .withReadTimeout(annotation.seconds, TimeUnit.SECONDS)
                    .withWriteTimeout(annotation.seconds, TimeUnit.SECONDS)
                    .proceed(newRequest.build())
            }
        }
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