package com.devper.template.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

abstract class PagingUseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {

    operator fun invoke(params: P): Flow<R> = execute(params)
        .flowOn(coroutineDispatcher)

    protected abstract fun execute(parameters: P): Flow<R>
}
