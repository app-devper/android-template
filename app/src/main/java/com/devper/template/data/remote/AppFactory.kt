package com.devper.template.data.remote

import com.devper.template.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object AppFactory {

    fun createOkHttpClient(interceptor: AppInterceptor): OkHttpClient = OkHttpClient.Builder().run {
        addInterceptor(interceptor)
        if (BuildConfig.DEBUG) {
            addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }
        readTimeout(30, TimeUnit.SECONDS)
        connectTimeout(30, TimeUnit.SECONDS)
        writeTimeout(30, TimeUnit.SECONDS)
        build()
    }

    inline fun <reified T> createCallService(okHttpClient: OkHttpClient, url: String): T {
        val retrofit = Retrofit.Builder().run {
            baseUrl(url)
            client(okHttpClient)
            addConverterFactory(GsonConverterFactory.create())
            build()
        }
        return retrofit.create(T::class.java)
    }

}


