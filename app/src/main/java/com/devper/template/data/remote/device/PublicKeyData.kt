package com.devper.template.data.remote.device

import com.google.gson.annotations.SerializedName

data class PublicKeyData(
    @SerializedName("publicKey")
    val publicKey: String
)

