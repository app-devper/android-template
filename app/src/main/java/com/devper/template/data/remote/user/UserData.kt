package com.devper.template.data.remote.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "users")
data class UserData(
    @SerializedName("_id")
    @PrimaryKey
    val id: String,
    @SerializedName("role")
    val role: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("email")
    val email: String?,
    @SerializedName("firstName")
    val firstName: String?,
    @SerializedName("lastName")
    val lastName: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("imageUrl")
    val imageUrl: String?
)