package com.devper.template.common.model

open class BaseResponse internal constructor(){
    val devMessage: String? = null
    val resCode: String? = null
    val resMessage: String? = null
    val serverTime: String? = null
    val status: Int = 0
}