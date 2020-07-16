package com.devper.template.domain.usecase.auth

import com.devper.template.domain.core.thread.CoroutineThreadDispatcher
import com.devper.template.domain.model.auth.LoginPinParam
import com.devper.template.domain.repository.AuthRepository
import com.devper.template.domain.usecase.UseCase

class LoginPinUseCase(
    dispatcher: CoroutineThreadDispatcher,
    private val repo: AuthRepository
) : UseCase<LoginPinParam, String>(dispatcher) {
    override suspend fun executeOnBackground(param: LoginPinParam): String {
        return repo.loginPin(param)
    }
}