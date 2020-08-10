package com.devper.template.domain.usecase.user

import com.devper.template.domain.core.ResultState
import com.devper.template.domain.core.thread.Dispatcher
import com.devper.template.domain.repository.UserRepository
import com.devper.template.domain.usecase.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ClearUserUseCase @Inject constructor(
    dispatcher: Dispatcher,
    private val repo: UserRepository
) : FlowUseCase<Unit, Unit>(dispatcher.io()) {

    override fun execute(parameters: Unit): Flow<ResultState<Unit>> {
        return flow {
            emit(ResultState.Loading())
            emit(ResultState.Success(repo.clearUser()))
        }
    }
}