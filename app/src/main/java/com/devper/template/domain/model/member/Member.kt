package com.devper.template.domain.model.member

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "members")
data class Member(
    @PrimaryKey
    @SerializedName("_id")
    val id: String,
    val createdDate: String,
    val firstName: String,
    val lastName: String,
    val profilePic: String,
    val senderId: String,
    val url: String
)