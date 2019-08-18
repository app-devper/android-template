package com.devper.common.api

import android.util.Log
import retrofit2.Response
import java.io.IOException

class ApiResponse<T> {

    val code: Int
    val body: T?
    val errorMessage: String?
    var status: Status

    constructor(t: Throwable) {
        code = 500
        body = null
        errorMessage = t.message
        t.printStackTrace()
        status = when (t) {
            is IOException -> Status.TIMEOUT
            is IllegalStateException -> Status.CONVERSION_ERROR
            else -> Status.OTHER_ERROR
        }
    }

    constructor(response: Response<T>) {
        code = response.code()
        if (response.isSuccessful) {
            status = Status.SUCCESS
            body = response.body()
            errorMessage = null
        } else {
            status = Status.ERROR
            var message: String? = null
            if (response.errorBody() != null) {
                try {
                    message = response.errorBody()?.string()
                } catch (ignored: Exception) {
                    Log.e("Parse", "error while parsing response")
                }
            }
            if (message == null || message.trim { it <= ' ' }.isEmpty()) {
                message = response.message()
            }
            body = null
            errorMessage = message
        }
    }

    val isSuccessful: Boolean
        get() = code in 200..299

    val resource: Resource<T>
        get() {
            return if (isSuccessful) {
                Resource.success(body)
            } else {
                Resource.error(status, errorMessage ?: "Unknown error", body)
            }
        }
}