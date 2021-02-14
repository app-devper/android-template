package com.devper.template.domain.usecase.auth

import com.devper.template.domain.repository.AuthRepository

import com.devper.template.domain.core.thread.Dispatcher
import com.devper.template.domain.provider.AppSessionProvider
import com.devper.template.domain.usecase.UseCase
import javax.inject.Inject

class KeepAliveUseCase @Inject constructor(
    dispatcher: Dispatcher,
    private val repo: AuthRepository,
    private val session: AppSessionProvider,
) : UseCase<Unit, Unit>(dispatcher.io()) {

    override suspend fun execute(params: Unit) {
        repo.keepAlive().also {
            session.accessToken = it
        }
    }
}