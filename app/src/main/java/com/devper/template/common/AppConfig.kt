package com.devper.template.common

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import com.devper.template.BuildConfig
import java.util.concurrent.TimeUnit

const val SPLASH_DELAY: Long = 3000 // 3 seconds

private const val URL = "https://common-api-app.herokuapp.com/"
const val MOVIE_URL = "https://api.themoviedb.org/"

val url: String
    get() {
        return URL
    }

fun createOkHttpClient(): OkHttpClient = OkHttpClient.Builder().run {
    if (BuildConfig.DEBUG) {
        addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
    }
    readTimeout(30, TimeUnit.SECONDS)
    connectTimeout(30, TimeUnit.SECONDS)
    writeTimeout(30, TimeUnit.SECONDS)
    build()
}

