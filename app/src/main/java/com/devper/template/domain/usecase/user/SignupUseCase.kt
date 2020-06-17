package com.devper.template.domain.usecase.user

import com.devper.template.core.extension.md5
import com.devper.template.domain.model.user.SignupParam
import com.devper.template.domain.usecase.UseCase

class SignupUseCase(private val repo: com.devper.template.domain.repository.UserRepository) : UseCase<SignupParam, Unit>() {
    override suspend fun executeOnBackground(param: com.devper.template.domain.model.user.SignupParam) {
        param.password = param.password?.md5()
        return repo.signup(param)
    }
}