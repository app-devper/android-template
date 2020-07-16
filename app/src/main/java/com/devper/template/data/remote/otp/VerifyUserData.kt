package com.devper.template.data.remote.otp
import com.google.gson.annotations.SerializedName

data class VerifyUserData(
    @SerializedName("refCode")
    val refCode: String,
    @SerializedName("channel")
    val channel: String,
    @SerializedName("expiredDate")
    val expiredDate: String,
    @SerializedName("userRefId")
    val userRefId: String
)