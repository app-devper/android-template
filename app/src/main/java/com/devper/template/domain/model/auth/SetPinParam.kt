package com.devper.template.domain.model.auth

data class SetPinParam(
    val actionToken: String,
    val pin: String
)