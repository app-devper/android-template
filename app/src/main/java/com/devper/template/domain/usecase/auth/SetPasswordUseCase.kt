package com.devper.template.domain.usecase.auth

import com.devper.template.AppConfig.LOGIN_ERROR
import com.devper.template.core.exception.AppException
import com.devper.template.core.extension.md5
import com.devper.template.domain.core.thread.CoroutineThreadDispatcher
import com.devper.template.domain.model.auth.SetPasswordParam
import com.devper.template.domain.repository.AuthRepository
import com.devper.template.domain.usecase.UseCase

class SetPasswordUseCase(
    dispatcher: CoroutineThreadDispatcher,
    private val repo: AuthRepository
) : UseCase<SetPasswordParam, Unit>(dispatcher) {
    override suspend fun executeOnBackground(param: SetPasswordParam) {
        if (param.actionToken.isEmpty() || param.password.isEmpty()) {
            throw AppException(LOGIN_ERROR, "")
        }
        param.password = param.password.md5()
        return repo.setPassword(param)
    }
}