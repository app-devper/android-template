package com.devper.template.domain.model.user

data class UserUpdateParam(
    var id: String= "",
    var firstName: String = "",
    var lastName: String = "",
    var phone: String = "",
    var email: String = "",
    var gender: String = "NONE"
)
