package com.devper.template.domain.usecase.user

import com.devper.template.core.extension.md5
import com.devper.template.core.model.user.SignupParam
import com.devper.template.core.repository.UserRepository
import com.devper.template.domain.usecase.UseCase

class SignupUseCase(private val repo: UserRepository) : UseCase<SignupParam, Unit>() {
    override suspend fun executeOnBackground(param: SignupParam) {
        param.password = param.password?.md5()
        return repo.signup(param)
    }
}