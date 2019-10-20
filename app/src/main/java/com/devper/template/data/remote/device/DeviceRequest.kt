package com.devper.template.data.remote.device

import com.google.gson.annotations.SerializedName

data class DeviceRequest(
    @SerializedName("deviceId")
    val deviceId: String,
    @SerializedName("uuid")
    val uuid: String,
    @SerializedName("apiLevel")
    val apiLevel: String,
    @SerializedName("board")
    val board: String,
    @SerializedName("bootLoader")
    val bootLoader: String,
    @SerializedName("brand")
    val brand: String,
    @SerializedName("buildId")
    val buildId: String,
    @SerializedName("buildTime")
    val buildTime: String,
    @SerializedName("fingerprint")
    val fingerprint: String,
    @SerializedName("hardware")
    val hardware: String,
    @SerializedName("host")
    val host: String,
    @SerializedName("model")
    val model: String,
    @SerializedName("user")
    val user: String,
    @SerializedName("screenDensity")
    val screenDensity: String,
    @SerializedName("screenResolution")
    val screenResolution: String
)