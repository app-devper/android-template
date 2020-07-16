package com.devper.template.domain.usecase.device

import com.devper.template.domain.core.thread.CoroutineThreadDispatcher
import com.devper.template.domain.repository.DeviceRepository
import com.devper.template.domain.usecase.UseCase

class RegisterDeviceUseCase(
    dispatcher: CoroutineThreadDispatcher,
    private val repo: DeviceRepository
) : UseCase<String?, Boolean>(dispatcher) {
    override suspend fun executeOnBackground(param: String?): Boolean {
        return repo.registerDevice()
    }
}