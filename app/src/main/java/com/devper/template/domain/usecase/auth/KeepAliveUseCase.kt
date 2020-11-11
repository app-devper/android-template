package com.devper.template.domain.usecase.auth

import com.devper.template.data.remote.auth.AuthRepository
import com.devper.template.data.session.AppSessionProvider
import com.devper.template.core.thread.Dispatcher
import com.devper.template.domain.usecase.UseCase
import javax.inject.Inject

class KeepAliveUseCase @Inject constructor(
    dispatcher: Dispatcher,
    private val repo: AuthRepository,
    private val session: AppSessionProvider,
) : UseCase<Unit, Unit>(dispatcher.io()) {

    override suspend fun execute(params: Unit) {
        val accessToken = repo.keepAlive()
        session.accessToken = accessToken
    }
}