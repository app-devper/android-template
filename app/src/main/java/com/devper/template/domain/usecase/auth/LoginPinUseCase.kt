package com.devper.template.domain.usecase.auth

import com.devper.template.data.remote.auth.AuthRepository
import com.devper.template.data.session.AppSessionProvider
import com.devper.template.domain.core.ResultState
import com.devper.template.core.thread.Dispatcher
import com.devper.template.domain.model.auth.LoginPinParam
import com.devper.template.domain.usecase.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginPinUseCase @Inject constructor(
    dispatcher: Dispatcher,
    private val repo: AuthRepository,
    private val session: AppSessionProvider,
) : FlowUseCase<LoginPinParam, Unit>(dispatcher.io()) {

    override fun execute(params: LoginPinParam): Flow<ResultState<Unit>> {
        return flow {
            emit(ResultState.Loading())
            session.accessToken = repo.loginPin(params)
            emit(ResultState.Success(Unit))
        }
    }
}