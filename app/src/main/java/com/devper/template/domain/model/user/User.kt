package com.devper.template.domain.model.user

import android.os.Parcelable
import android.view.View
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: String,
    val role: String,
    val username: String,
    val email: String?,
    val firstName: String?,
    val lastName: String?,
    val phone: String?,
    val imageUrl: String?,
    val status: String?,
    val pin: String? = null,
) : Parcelable {

    val fullName: String
        get() = "$firstName $lastName"

    val admin: Int
        get() = if (role == "ADMIN") View.VISIBLE else View.GONE
}
