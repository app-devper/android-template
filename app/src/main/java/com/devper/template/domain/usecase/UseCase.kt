package com.devper.template.domain.usecase

import com.devper.template.core.exception.AppException
import com.devper.template.core.platform.ErrorCode
import com.devper.template.domain.core.ErrorResponse
import com.google.gson.Gson
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.coroutines.CoroutineContext

typealias CompletionBlock<T> = UseCase.Request<T>.() -> Unit

abstract class UseCase<Param, T> {
    private var parentJob: Job = Job()
    private var backgroundContext: CoroutineContext = Dispatchers.IO
    private var foregroundContext: CoroutineContext = Dispatchers.Main

    protected abstract suspend fun executeOnBackground(param: Param): T

    fun execute(param: Param, block: CompletionBlock<T>) {
        val response = Request<T>().apply { block() }
        response.invoke()
        unsubscribe()
        parentJob = Job()
        CoroutineScope(foregroundContext + parentJob).launch {
            try {
                val result = withContext(backgroundContext) {
                    executeOnBackground(param)
                }
                response.invoke(result)
            } catch (e: Exception) {
                response.invoke(toAppError(e))
            }
        }
    }

    fun unsubscribe() {
        parentJob.apply {
            cancelChildren()
            cancel()
        }
    }

    class Request<T> {
        private var onStart: (() -> Unit) = {}
        private var onComplete: ((T) -> Unit) = {}
        private var onError: ((AppException) -> Unit) = {}

        fun onStart(block: () -> Unit) {
            onStart = block
        }

        fun onComplete(block: (T) -> Unit) {
            onComplete = block
        }

        fun onError(block: (AppException) -> Unit) {
            onError = block
        }

        operator fun invoke(result: T) {
            onComplete.invoke(result)
        }

        operator fun invoke(error: AppException) {
            onError.invoke(error)
        }

        operator fun invoke() {
            onStart.invoke()
        }
    }

    private fun toAppError(throwable: Throwable): AppException {
        return when (throwable) {
            is HttpException -> {
                try {
                    val result = throwable.response()?.errorBody()?.string()
                    val response = Gson().fromJson(result, ErrorResponse::class.java)
                    AppException(response.resCode, response.resMessage)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    AppException(ErrorCode.ERROR.name, e.message ?: "")
                }
            }
            is CancellationException -> {
                AppException(ErrorCode.CANCEL.name, throwable.message ?: "")
            }
            is SocketTimeoutException -> {
                AppException(ErrorCode.TIMEOUT.name, throwable.message ?: "")
            }
            is IOException -> {
                AppException(ErrorCode.CONVERSION_ERROR.name, throwable.message ?: "")
            }
            is UnknownHostException -> {
                AppException(ErrorCode.CONVERSION_ERROR.name, throwable.message ?: "")
            }
            else -> {
                AppException(ErrorCode.OTHER_ERROR.name, throwable.message ?: "")
            }
        }
    }

}