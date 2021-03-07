package com.devper.template.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

abstract class PagingUseCase<in P, R>(private val dispatcher: CoroutineDispatcher) {

    operator fun invoke(params: P): Flow<R> = execute(params)
        .flowOn(dispatcher)

    protected abstract fun execute(params: P): Flow<R>
}
