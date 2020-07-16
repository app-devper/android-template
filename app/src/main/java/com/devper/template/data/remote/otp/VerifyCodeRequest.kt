package com.devper.template.data.remote.otp

import com.google.gson.annotations.SerializedName

data class VerifyCodeRequest(
    @SerializedName("userRefId")
    val userRefId: String,
    @SerializedName("refCode")
    val refCode: String,
    @SerializedName("code")
    val code: String
)