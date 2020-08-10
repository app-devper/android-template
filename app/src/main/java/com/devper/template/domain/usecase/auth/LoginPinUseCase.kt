package com.devper.template.domain.usecase.auth

import com.devper.template.domain.core.ResultState
import com.devper.template.domain.core.thread.Dispatcher
import com.devper.template.domain.model.auth.LoginPinParam
import com.devper.template.domain.repository.AuthRepository
import com.devper.template.domain.usecase.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginPinUseCase @Inject constructor(
    dispatcher: Dispatcher,
    private val repo: AuthRepository
) : FlowUseCase<LoginPinParam, String>(dispatcher.io()) {

    override fun execute(parameters: LoginPinParam): Flow<ResultState<String>> {
        return flow {
            emit(ResultState.Loading())
            emit(ResultState.Success(repo.loginPin(parameters)))
        }
    }
}