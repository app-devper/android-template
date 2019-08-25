package com.devper.template.app.model

import com.google.gson.annotations.SerializedName

open class BaseResponse {
    @SerializedName("devMessage")
    val devMessage: String? = null
    @SerializedName("resCode")
    val resCode: String? = null
    @SerializedName("resMessage")
    val resMessage: String? = null
    @SerializedName("serverTime")
    val serverTime: String? = null
    @SerializedName("status")
    val code: Int = 500
}
