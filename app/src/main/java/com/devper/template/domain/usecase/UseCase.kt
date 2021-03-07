package com.devper.template.domain.usecase

import com.devper.template.domain.core.ResultState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber

abstract class UseCase<in P, R>(private val dispatcher: CoroutineDispatcher) {

    suspend operator fun invoke(params: P): ResultState<R> {
        return try {
            withContext(dispatcher) {
                execute(params).let {
                    ResultState.Success(it)
                }
            }
        } catch (e: Exception) {
            Timber.d(e)
            ResultState.Error(e)
        }
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(params: P): R
}
