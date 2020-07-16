package com.devper.template.data.remote.auth

import com.google.gson.annotations.SerializedName

data class LoginPinRequest(
    @SerializedName("username")
    val username: String,
    @SerializedName("pin")
    val pin: String,
    @SerializedName("channel")
    val channel: String
)