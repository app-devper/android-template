package com.devper.template.domain.usecase.auth

import com.devper.template.AppConfig.LOGIN_ERROR
import com.devper.template.core.exception.AppException
import com.devper.template.core.extension.md5
import com.devper.template.domain.core.thread.CoroutineThreadDispatcher
import com.devper.template.domain.model.auth.LoginParam
import com.devper.template.domain.repository.AuthRepository
import com.devper.template.domain.usecase.UseCase

class LoginUseCase(
    dispatcher: CoroutineThreadDispatcher,
    private val repo: AuthRepository
) : UseCase<LoginParam, String>(dispatcher) {
    override suspend fun executeOnBackground(param: LoginParam):String {
        if (param.username.isEmpty() || param.password.isEmpty()) {
            throw AppException(LOGIN_ERROR, "")
        }
        param.password = param.password.md5()
        return repo.login(param)
    }
}