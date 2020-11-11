package com.devper.template.domain.usecase.user

import com.devper.template.data.preference.PreferenceStorage
import com.devper.template.data.remote.user.UserRepository
import com.devper.template.data.session.AppSessionProvider
import com.devper.template.core.thread.Dispatcher
import com.devper.template.domain.usecase.UseCase
import javax.inject.Inject

class ClearUserUseCase @Inject constructor(
    dispatcher: Dispatcher,
    private val repo: UserRepository,
    private val pref: PreferenceStorage,
    private val session: AppSessionProvider,
) : UseCase<Unit, Unit>(dispatcher.io()) {

    override suspend fun execute(params: Unit) {
        session.accessToken = null
        repo.clearUser()
        pref.clear()
    }
}