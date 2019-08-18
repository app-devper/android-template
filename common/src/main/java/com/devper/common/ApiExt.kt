package com.devper.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.devper.common.api.ApiResponse
import com.devper.common.api.Resource
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.atomic.AtomicBoolean

inline fun <reified T> createCallService(okHttpClient: OkHttpClient, url: String): T {
    val retrofit = Retrofit.Builder().run {
        baseUrl(url)
        client(okHttpClient)
        addConverterFactory(GsonConverterFactory.create())
        build()
    }
    return retrofit.create(T::class.java)
}

inline fun <reified R> Call<R>.toResource(): LiveData<Resource<R>> {
    val call = this
    return object : LiveData<Resource<R>>() {
        var started = AtomicBoolean(false)
        override fun onActive() {
            if (started.compareAndSet(false, true)) {
                postValue(Resource.loading(null))
                call.enqueue(object : Callback<R> {
                    override fun onResponse(call: Call<R>, response: Response<R>) {
                        postValue(ApiResponse(response).resource)
                    }

                    override fun onFailure(call: Call<R>, throwable: Throwable) {
                        postValue(ApiResponse<R>(throwable).resource)
                    }
                })
            }
        }
    }
}

inline fun <T> Call<T>.toApiResponse(crossinline successHandler: (ApiResponse<T>) -> Unit, crossinline failureHandler: (ApiResponse<T>) -> Unit) {
    this.enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            if (response.isSuccessful) {
                successHandler(ApiResponse(response))
            } else {
                failureHandler(ApiResponse(response))
            }
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            failureHandler(ApiResponse(t))
        }
    })
}
