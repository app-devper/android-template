package com.devper.template.data.remote.auth

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("username")
    val username: String,
    @SerializedName("pwd")
    val password: String,
    @SerializedName("channel")
    val channel: String
)