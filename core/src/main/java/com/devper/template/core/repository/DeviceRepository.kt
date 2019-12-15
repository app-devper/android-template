package com.devper.template.core.repository

interface DeviceRepository {

    suspend fun registerDevice(): String

}