package com.devper.template.domain.model.user

data class SignUpParam(
    var username: String = "",
    var email: String = "",
    var phone: String = "",
    var firstName: String? = null,
    var lastName: String? = null
)