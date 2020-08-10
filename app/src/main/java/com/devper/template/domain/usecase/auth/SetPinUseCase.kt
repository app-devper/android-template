package com.devper.template.domain.usecase.auth

import com.devper.template.domain.core.ResultState
import com.devper.template.domain.core.thread.Dispatcher
import com.devper.template.domain.model.auth.SetPinParam
import com.devper.template.domain.repository.AuthRepository
import com.devper.template.domain.usecase.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SetPinUseCase @Inject constructor(
    dispatcher: Dispatcher,
    private val repo: AuthRepository
) : FlowUseCase<SetPinParam, Unit>(dispatcher.io()) {

    override fun execute(parameters: SetPinParam): Flow<ResultState<Unit>> {
        return flow {
            emit(ResultState.Loading())
            emit(ResultState.Success(repo.setPin(parameters)))
        }
    }
}