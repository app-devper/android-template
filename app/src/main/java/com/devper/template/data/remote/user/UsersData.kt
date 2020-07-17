package com.devper.template.data.remote.user

import com.google.gson.annotations.SerializedName

data class UsersData(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<UserData>,
    @SerializedName("total")
    val total: Int,
    @SerializedName("totalPages")
    val totalPages: Int
)