package com.devper.template.data.remote.otp

import com.google.gson.annotations.SerializedName

data class VerifyUserRequest(
    @SerializedName("userRefId")
    val userRefId: String,
    @SerializedName("channel")
    val channel: String
)