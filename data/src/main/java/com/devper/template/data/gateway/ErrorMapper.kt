package com.devper.template.data.gateway

import com.devper.template.core.exception.AppException
import com.devper.template.data.remote.error.ErrorResponse
import com.google.gson.Gson
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

enum class Status {
    ERROR,
    TIMEOUT,
    CANCEL,
    CONVERSION_ERROR,
    OTHER_ERROR
}

suspend fun <T> safeApiCall(apiCall: suspend () -> T): T {
    return try {
        apiCall.invoke()
    } catch (throwable: Throwable) {
        when (throwable) {
            is HttpException -> {
                try {
                    val result = throwable.response()?.errorBody()?.string()
                    val response = Gson().fromJson(result, ErrorResponse::class.java)
                    throw AppException(response.resCode, response.resMessage)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    throw AppException(Status.ERROR.name, e.message ?: "")
                }
            }
            is CancellationException -> {
                throw AppException(Status.CANCEL.name, throwable.message ?: "")
            }
            is SocketTimeoutException -> {
                throw  AppException(Status.TIMEOUT.name, throwable.message ?: "")
            }
            is IOException -> {
                throw AppException(Status.CONVERSION_ERROR.name, throwable.message ?: "")
            }
            is UnknownHostException -> {
                throw AppException(Status.CONVERSION_ERROR.name, throwable.message ?: "")
            }
            else -> {
                throw AppException(Status.OTHER_ERROR.name, throwable.message ?: "")
            }

        }
    }
}