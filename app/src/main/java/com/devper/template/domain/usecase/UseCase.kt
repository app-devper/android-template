package com.devper.template.domain.usecase

import kotlinx.coroutines.*
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
                response.invoke(e)
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
        private var onStart: (() -> Unit)? = null
        private var onComplete: ((T) -> Unit)? = null
        private var onError: ((Exception) -> Unit)? = null
        private var onFinally: ((T?, Exception?) -> Unit)? = null

        fun onStart(block: () -> Unit) {
            onStart = block
        }

        fun onComplete(block: (T) -> Unit) {
            onComplete = block
        }

        fun onError(block: (Exception) -> Unit) {
            onError = block
        }

        fun onFinally(block: (T?, Exception?) -> Unit) {
            onFinally = block
        }

        operator fun invoke(result: T) {
            onComplete?.invoke(result)
            onFinally?.invoke(result, null)
        }

        operator fun invoke(error: Exception) {
            onError?.invoke(error)
            onFinally?.invoke(null, error)
        }

        operator fun invoke() {
            onStart?.invoke()
        }

    }

}