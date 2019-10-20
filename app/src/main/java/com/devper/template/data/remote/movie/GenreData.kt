package com.devper.template.data.remote.movie

import com.google.gson.annotations.SerializedName

data class GenreData(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("name")
    var name: String? = null
)
