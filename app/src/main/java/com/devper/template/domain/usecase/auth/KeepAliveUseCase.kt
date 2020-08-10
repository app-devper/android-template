package com.devper.template.domain.usecase.auth

import com.devper.template.domain.core.ResultState
import com.devper.template.domain.core.thread.Dispatcher
import com.devper.template.domain.repository.AuthRepository
import com.devper.template.domain.usecase.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class KeepAliveUseCase @Inject constructor(
    dispatcher: Dispatcher,
    private val repo: AuthRepository
) : FlowUseCase<Unit, String>(dispatcher.io()) {

    override fun execute(parameters: Unit): Flow<ResultState<String>> {
        return flow {
            emit(ResultState.Loading())
            emit(ResultState.Success(repo.keepAlive()))
        }
    }
}