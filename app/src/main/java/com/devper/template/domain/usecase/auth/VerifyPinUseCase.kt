package com.devper.template.domain.usecase.auth

import com.devper.template.domain.core.ResultState
import com.devper.template.domain.core.thread.Dispatcher
import com.devper.template.domain.model.auth.PinParam
import com.devper.template.domain.model.auth.Verify
import com.devper.template.domain.repository.AuthRepository
import com.devper.template.domain.usecase.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VerifyPinUseCase @Inject constructor(
    dispatcher: Dispatcher,
    private val repo: AuthRepository
) : FlowUseCase<PinParam, Verify>(dispatcher.io()) {

    override fun execute(parameters: PinParam): Flow<ResultState<Verify>> {
        return flow {
            emit(ResultState.Loading())
            emit(ResultState.Success(repo.verifyPin(parameters)))
        }
    }
}