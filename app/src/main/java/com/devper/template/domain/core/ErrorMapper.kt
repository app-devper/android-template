package com.devper.template.domain.core

import com.devper.template.AppConfig
import com.devper.template.core.exception.AppException
import com.devper.template.core.exception.InternetException
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ErrorMapper {
    fun toAppError(throwable: Throwable?): AppException {
        return when (throwable) {
            is AppException -> {
                throwable
            }
            is HttpException -> {
                var code: String = AppConfig.DATA_ERROR
                var message: String? = null
                var result: String? = null
                throwable.response()?.let {
                    code = it.code().toString()
                    message = it.message()
                    result = it.errorBody()?.string()
                }
                try {
                    val response = Gson().fromJson(result, ErrorResponse::class.java)
                    AppException(response.resCode, response.resMessage)
                } catch (e: Exception) {
                    AppException(code, message ?: e.message ?: "")
                }
            }
            is InternetException -> {
                AppException(AppConfig.NO_INTERNET_ERROR, throwable.message ?: "")
            }
            is SocketTimeoutException -> {
                AppException(AppConfig.TIME_OUT_ERROR, throwable.message ?: "")
            }
            is IOException -> {
                AppException(AppConfig.CONNECTION_ERROR, throwable.message ?: "")
            }
            is UnknownHostException -> {
                AppException(AppConfig.UN_KNOWN_ERROR, throwable.message ?: "")
            }
            is JsonSyntaxException -> {
                AppException(AppConfig.PARSE_JSON_ERROR, throwable.message ?: "")
            }
            is IllegalArgumentException -> {
                AppException(AppConfig.INVALID_DATA_ERROR, throwable.message ?: "")
            }
            else -> {
                AppException(AppConfig.OTHER_ERROR, throwable?.message ?: "")
            }
        }
    }
}