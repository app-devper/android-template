package com.devper.template.domain.repository

interface DeviceRepository {

    suspend fun registerDevice(): String

}