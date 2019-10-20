package com.devper.template.data.repository

import com.devper.template.data.remote.device.DeviceRequest
import com.devper.template.data.remote.user.LoginRequest
import com.devper.template.data.remote.user.SignupRequest
import com.devper.template.domain.model.application.AppInfo
import com.devper.template.domain.model.user.LoginParam
import com.devper.template.domain.model.user.SignupParam

class DeviceMapper {

    fun toRequest(data: AppInfo): DeviceRequest {
        return DeviceRequest(
            data.androidId, data.uuid, data.apiLevel, data.board, data.bootLoader, data.brand, data.buildId, data.buildTime,
            data.fingerprint, data.hardware, data.host, data.model, data.user, data.screenDensity, data.screenResolution
        )
    }

}