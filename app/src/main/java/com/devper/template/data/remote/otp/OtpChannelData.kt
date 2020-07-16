package com.devper.template.data.remote.otp
import com.google.gson.annotations.SerializedName

data class OtpChannelData(
    @SerializedName("channels")
    val channels: List<ChannelData>,
    @SerializedName("userRefId")
    val userRefId: String
)

