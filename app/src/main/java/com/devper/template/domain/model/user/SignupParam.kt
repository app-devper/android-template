package com.devper.template.domain.model.user

data class SignupParam(
    var username: String = "",
    var password: String = "",
    var email: String = "",
    var firstName: String? = null,
    var lastName: String? = null,
    var phone: String? = null
)