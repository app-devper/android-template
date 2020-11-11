package com.devper.template.data.remote.device

interface DeviceRepository {

    suspend fun registerDevice(): String

}