package com.devper.template.data.repository

import com.devper.template.data.device.AndroidAppInfo
import com.devper.template.data.remote.AppService
import com.devper.template.domain.repository.DeviceRepository

class DeviceRemoteRepository(
    private val api: AppService,
    private val appInfo: AndroidAppInfo
) : DeviceRepository {
    override suspend fun registerDevice(): String {
        val mapper = DeviceMapper()
        return api.registerDevice(mapper.toRequest(appInfo)).data.publicKey
    }
}