package com.devper.template.domain.usecase.auth

import com.devper.template.core.extension.md5
import com.devper.template.domain.core.thread.CoroutineThreadDispatcher
import com.devper.template.domain.model.auth.PasswordParam
import com.devper.template.domain.model.auth.Verify
import com.devper.template.domain.repository.AuthRepository
import com.devper.template.domain.usecase.UseCase

class VerifyPasswordUseCase(
    dispatcher: CoroutineThreadDispatcher,
    private val repo: AuthRepository
) : UseCase<PasswordParam, Verify>(dispatcher) {
    override suspend fun executeOnBackground(param: PasswordParam): Verify {
        param.password =  param.password.md5()
        return repo.verifyPassword(param)
    }
}