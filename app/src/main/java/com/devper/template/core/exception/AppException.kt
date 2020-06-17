package com.devper.template.core.exception

class AppException(val resultCode: String, private val msg: String, var description: String? = null) : Exception(msg) {

    fun getDesc(): String {
        return description ?: msg
    }

}