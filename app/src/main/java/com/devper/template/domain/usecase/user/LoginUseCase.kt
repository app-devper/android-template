package com.devper.template.domain.usecase.user

import com.devper.template.core.ext.md5
import com.devper.template.domain.model.user.LoginParam
import com.devper.template.domain.repository.UserRepository
import com.devper.template.domain.model.user.User
import com.devper.template.domain.usecase.UseCase

class LoginUseCase(private val repo: UserRepository) : UseCase<LoginParam, User>() {
    override suspend fun executeOnBackground(param: LoginParam): User {
        param.password = param.password.md5()
        return repo.login(param)
    }
}