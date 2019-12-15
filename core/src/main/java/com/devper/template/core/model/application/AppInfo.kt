package com.devper.template.core.model.application

interface AppInfo {
    val appId: String
    val name: String
    val imei: String
    val imsi: String
    val uuid: String
    val androidId: String
    val apiLevel: String
    val board: String
    val bootLoader: String
    val brand: String
    val buildId: String
    val buildTime: String
    val fingerprint: String
    val hardware: String
    val host: String
    val model: String
    val serialNo: String
    val user: String
    val screenDensity: String
    val screenResolution: String
}