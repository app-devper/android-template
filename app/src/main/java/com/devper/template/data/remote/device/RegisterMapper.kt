package com.devper.template.data.remote.device

import com.devper.template.domain.provider.AppInfoProvider

class RegisterMapper {

    fun toRequest(data: AppInfoProvider): RegisterRequest {
        return RegisterRequest(
            data.androidId,
            data.uuid,
            data.apiLevel,
            data.board,
            data.bootLoader,
            data.brand,
            data.buildId,
            data.buildTime,
            data.fingerprint,
            data.hardware,
            data.host,
            data.model,
            data.user,
            data.screenDensity,
            data.screenResolution
        )
    }

}