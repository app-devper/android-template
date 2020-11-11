package com.devper.template.domain.usecase.preference

import com.devper.template.core.thread.Dispatcher
import com.devper.template.data.preference.PreferenceStorage
import com.devper.template.domain.usecase.UseCase
import javax.inject.Inject

class HavePinUseCase @Inject constructor(
    dispatcher: Dispatcher,
    private val pref: PreferenceStorage
) : UseCase<Unit, Boolean>(dispatcher.io()) {

    override suspend fun execute(params: Unit): Boolean {
        if (pref.userId.isNotEmpty()) {
            return pref.pin.isNotEmpty()
        }
        return false
    }
}