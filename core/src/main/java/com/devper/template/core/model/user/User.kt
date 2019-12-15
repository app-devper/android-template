package com.devper.template.core.model.user

data class User(
    val id: String,
    val username: String?,
    val email: String?,
    val firstName: String?,
    val lastName: String?,
    val phone: String?,
    val imageUrl: String?
) {
    val fullName: String
        get() = "$firstName $lastName"
}
