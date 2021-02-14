package com.devper.template.domain.usecase.preference

import com.devper.template.domain.core.thread.Dispatcher
import com.devper.template.domain.provider.PreferenceProvider
import com.devper.template.domain.usecase.UseCase
import javax.inject.Inject

class GetUserIdUseCase @Inject constructor(
    dispatcher: Dispatcher,
    private val pref: PreferenceProvider
) : UseCase<Unit, String>(dispatcher.io()) {

    override suspend fun execute(params: Unit): String {
        if (pref.pin.isEmpty()) {
            return ""
        }
        return pref.userId
    }
}