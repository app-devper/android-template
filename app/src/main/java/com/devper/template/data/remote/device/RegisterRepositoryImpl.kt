package com.devper.template.data.remote.device

import com.devper.template.data.remote.ApiService
import com.devper.template.domain.provider.AppInfoProvider
import com.devper.template.domain.repository.DeviceRepository
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val appInfo: AppInfoProvider
) : DeviceRepository {
    override suspend fun registerDevice(): String {
        val mapper = RegisterMapper()
        return api.registerDevice(mapper.toRequest(appInfo)).let {
            ""
        }
    }
}