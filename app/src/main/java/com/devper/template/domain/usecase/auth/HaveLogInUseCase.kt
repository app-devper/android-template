package com.devper.template.domain.usecase.auth

import com.devper.template.domain.core.thread.Dispatcher
import com.devper.template.domain.provider.AppSessionProvider
import com.devper.template.domain.usecase.UseCase
import javax.inject.Inject

class HaveLogInUseCase @Inject constructor(
    dispatcher: Dispatcher,
    private val session: AppSessionProvider,
) : UseCase<Unit, Boolean>(dispatcher.io()) {

    override suspend fun execute(params: Unit): Boolean {
        return !session.accessToken.isNullOrEmpty()
    }
}