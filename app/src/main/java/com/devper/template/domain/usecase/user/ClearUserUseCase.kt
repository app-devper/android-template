package com.devper.template.domain.usecase.user

import com.devper.template.domain.provider.PreferenceProvider
import com.devper.template.domain.repository.UserRepository
import com.devper.template.domain.core.thread.Dispatcher
import com.devper.template.domain.provider.AppSessionProvider
import com.devper.template.domain.usecase.UseCase
import javax.inject.Inject

class ClearUserUseCase @Inject constructor(
    dispatcher: Dispatcher,
    private val repo: UserRepository,
    private val pref: PreferenceProvider,
    private val session: AppSessionProvider,
) : UseCase<Unit, Unit>(dispatcher.io()) {

    override suspend fun execute(params: Unit) {
        session.accessToken = null
        repo.clearUser()
        pref.clear()
    }
}