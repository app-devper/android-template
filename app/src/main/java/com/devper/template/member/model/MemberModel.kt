package com.devper.template.member.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.devper.template.common.model.BaseResponse
import com.google.gson.annotations.SerializedName

data class MemberResponse(
    val data: Result?
) : BaseResponse()

data class Result(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<Member>?
)

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