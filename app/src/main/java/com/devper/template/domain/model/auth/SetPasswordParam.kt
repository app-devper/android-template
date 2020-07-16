package com.devper.template.domain.model.auth

data class SetPasswordParam(
    val actionToken: String,
    var password: String
)