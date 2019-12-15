package com.devper.template.data.remote.error

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("devMessage")
    val devMessage: String,
    @SerializedName("resCode")
    val resCode: String,
    @SerializedName("resMessage")
    val resMessage: String,
    @SerializedName("serverTime")
    val serverTime: String,
    @SerializedName("status")
    val code: Int = 500
)