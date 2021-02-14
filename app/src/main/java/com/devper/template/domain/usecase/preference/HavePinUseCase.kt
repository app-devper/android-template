package com.devper.template.domain.usecase.preference

import com.devper.template.domain.core.thread.Dispatcher
import com.devper.template.domain.provider.PreferenceProvider
import com.devper.template.domain.usecase.UseCase
import javax.inject.Inject

class HavePinUseCase @Inject constructor(
    dispatcher: Dispatcher,
    private val pref: PreferenceProvider
) : UseCase<Unit, Boolean>(dispatcher.io()) {

    override suspend fun execute(params: Unit): Boolean {
        if (pref.userId.isNotEmpty()) {
            return pref.pin.isNotEmpty()
        }
        return false
    }
}