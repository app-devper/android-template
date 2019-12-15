package com.devper.template.core.model.user

data class SignupParam(
    var username: String? = null,
    var password: String? = null,
    var email: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var phone: String? = null
)