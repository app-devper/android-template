package com.devper.template.domain.usecase.user

import com.devper.template.core.extension.md5
import com.devper.template.core.model.user.LoginParam
import com.devper.template.core.repository.UserRepository
import com.devper.template.core.model.user.User
import com.devper.template.domain.usecase.UseCase

class LoginUseCase(private val repo: UserRepository) : UseCase<LoginParam, User>() {
    override suspend fun executeOnBackground(param: LoginParam): User {
        param.password = param.password.md5()
        return repo.login(param)
    }
}