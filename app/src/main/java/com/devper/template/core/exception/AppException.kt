package com.devper.template.core.exception

class AppException(val resultCode: String, msg: String, private var description: String? = null) : Exception(msg) {

    fun getDesc(): String {
        return description ?: ""
    }

}