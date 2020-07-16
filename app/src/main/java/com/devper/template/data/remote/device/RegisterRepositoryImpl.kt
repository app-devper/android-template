package com.devper.template.data.remote.device

import com.devper.template.data.preference.AppPreference
import com.devper.template.data.remote.ApiService
import com.devper.template.domain.provider.AppInfoProvider
import com.devper.template.domain.repository.DeviceRepository

class RegisterRepositoryImpl(
    private val api: ApiService,
    private val appInfo: AppInfoProvider,
    private val pref: AppPreference
) : DeviceRepository {
    override suspend fun registerDevice(): Boolean {
        val mapper = RegisterMapper()
        return api.registerDevice(mapper.toRequest(appInfo)).let {
            pref.isSetPin
        }
    }
}