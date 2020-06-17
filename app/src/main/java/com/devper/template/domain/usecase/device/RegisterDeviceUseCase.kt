package com.devper.template.domain.usecase.device

import com.devper.template.domain.usecase.UseCase

class RegisterDeviceUseCase(private val repo: com.devper.template.domain.repository.DeviceRepository) : UseCase<String?, String>() {
    override suspend fun executeOnBackground(param: String?): String {
        return repo.registerDevice()
    }
}