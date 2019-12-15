package com.devper.template.data.gateway

import com.devper.template.core.model.application.AppInfo
import com.devper.template.core.repository.DeviceRepository
import com.devper.template.data.remote.ApiService

class DeviceRepositoryImpl(
    private val api: ApiService,
    private val appInfo: AppInfo
) : DeviceRepository {
    override suspend fun registerDevice(): String {
        val mapper = DeviceMapper()
        return safeApiCall { api.registerDevice(mapper.toRequest(appInfo)) }.publicKey
    }
}