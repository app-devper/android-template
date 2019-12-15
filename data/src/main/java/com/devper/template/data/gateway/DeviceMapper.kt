package com.devper.template.data.gateway

import com.devper.template.data.remote.device.DeviceRequest
import com.devper.template.core.model.application.AppInfo

class DeviceMapper {

    fun toRequest(data: AppInfo): DeviceRequest {
        return DeviceRequest(
            data.androidId, data.uuid, data.apiLevel, data.board, data.bootLoader, data.brand, data.buildId, data.buildTime,
            data.fingerprint, data.hardware, data.host, data.model, data.user, data.screenDensity, data.screenResolution
        )
    }

}