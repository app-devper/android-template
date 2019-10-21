package com.devper.template.data.remote.device

import com.google.gson.annotations.SerializedName

data class DeviceData(
    @SerializedName("publicKey")
    val publicKey: String
)

