package com.devper.template.domain.usecase

import com.devper.template.domain.core.thread.CoroutineThreadDispatcher
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

typealias CompletionBlock<T> = UseCase.Request<T>.() -> Unit

abstract class UseCase<Param, T>(dispatcher: CoroutineThreadDispatcher) {
    private var parentJob: Job = Job()
    private var backgroundContext: CoroutineContext = dispatcher.io()
    private var foregroundContext: CoroutineContext = dispatcher.ui()

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
                e.printStackTrace()
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
        private var onStart: (() -> Unit) = {}
        private var onComplete: ((T) -> Unit) = {}
        private var onError: ((Exception) -> Unit) = {}

        fun onStart(block: () -> Unit) {
            onStart = block
        }

        fun onComplete(block: (T) -> Unit) {
            onComplete = block
        }

        fun onError(block: (Exception) -> Unit) {
            onError = block
        }

        operator fun invoke(result: T) {
            onComplete.invoke(result)
        }

        operator fun invoke(error: Exception) {
            onError.invoke(error)
        }

        operator fun invoke() {
            onStart.invoke()
        }
    }

}