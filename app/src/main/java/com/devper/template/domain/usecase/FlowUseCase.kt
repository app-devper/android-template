package com.devper.template.domain.usecase

import com.devper.template.domain.core.ResultState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

abstract class FlowUseCase<in P, R>(private val dispatcher: CoroutineDispatcher) {

    operator fun invoke(params: P): Flow<ResultState<R>> = execute(params)
        .catch { e ->
            emit(ResultState.Error(e))
        }
        .flowOn(dispatcher)

    protected abstract fun execute(params: P): Flow<ResultState<R>>
}
