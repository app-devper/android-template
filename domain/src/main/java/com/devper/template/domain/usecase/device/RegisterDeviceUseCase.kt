package com.devper.template.domain.usecase.device

import com.devper.template.core.repository.DeviceRepository
import com.devper.template.domain.usecase.UseCase

class RegisterDeviceUseCase(private val repo: DeviceRepository) : UseCase<String?, String>() {
    override suspend fun executeOnBackground(param: String?): String {
        return repo.registerDevice()
    }
}