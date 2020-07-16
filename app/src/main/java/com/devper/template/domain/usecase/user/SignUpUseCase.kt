package com.devper.template.domain.usecase.user

import com.devper.template.core.extension.md5
import com.devper.template.domain.core.thread.CoroutineThreadDispatcher
import com.devper.template.domain.model.user.SignUpParam
import com.devper.template.domain.repository.UserRepository
import com.devper.template.domain.usecase.UseCase

class SignUpUseCase(
    dispatcher: CoroutineThreadDispatcher,
    private val repo: UserRepository
) : UseCase<SignUpParam, Unit>(dispatcher) {
    override suspend fun executeOnBackground(param: SignUpParam) {
        param.password = param.password.md5()
        return repo.signUp(param)
    }
}