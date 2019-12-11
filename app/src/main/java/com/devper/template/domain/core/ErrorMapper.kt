package com.devper.template.domain.core

import com.devper.common.api.Status
import com.devper.template.core.exception.AppException
import com.google.gson.Gson
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ErrorMapper {

    fun toErrorException(throwable: Throwable?): AppException {
        return when (throwable) {
            is HttpException -> {
                try {
                    val result = throwable.response()?.errorBody()?.string()
                    val response = Gson().fromJson(result, ErrorResponse::class.java)
                    AppException(response.resCode, response.resMessage)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    AppException(Status.ERROR.name, e.message ?: "")
                }
            }
            is CancellationException -> {
                AppException(Status.CANCEL.name, throwable.message ?: "")
            }
            is SocketTimeoutException -> {
                AppException(Status.TIMEOUT.name, throwable.message ?: "")
            }
            is IOException -> {
                AppException(Status.CONVERSION_ERROR.name, throwable.message ?: "")
            }
            is UnknownHostException -> {
                AppException(Status.CONVERSION_ERROR.name, throwable.message ?: "")
            }
            else -> {
                AppException(Status.OTHER_ERROR.name, throwable?.message ?: "")
            }
        }

    }
}