package com.devper.template.data.remote.movie

import com.google.gson.annotations.SerializedName

data class GenreData(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)
