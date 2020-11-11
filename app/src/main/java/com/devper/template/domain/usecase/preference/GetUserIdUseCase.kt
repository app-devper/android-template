package com.devper.template.domain.usecase.preference

import com.devper.template.core.thread.Dispatcher
import com.devper.template.data.preference.PreferenceStorage
import com.devper.template.domain.usecase.UseCase
import javax.inject.Inject

class GetUserIdUseCase @Inject constructor(
    dispatcher: Dispatcher,
    private val pref: PreferenceStorage
) : UseCase<Unit, String>(dispatcher.io()) {

    override suspend fun execute(params: Unit): String {
        if (pref.pin.isEmpty()) {
            return ""
        }
        return pref.userId
    }
}