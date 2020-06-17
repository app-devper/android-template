package com.devper.template.domain.usecase.user

import com.devper.template.core.extension.md5
import com.devper.template.domain.model.user.LoginParam
import com.devper.template.domain.model.user.User
import com.devper.template.domain.usecase.UseCase

class LoginUseCase(private val repo: com.devper.template.domain.repository.UserRepository) : UseCase<LoginParam, User>() {
    override suspend fun executeOnBackground(param: com.devper.template.domain.model.user.LoginParam): com.devper.template.domain.model.user.User {
        param.password = param.password.md5()
        return repo.login(param)
    }
}