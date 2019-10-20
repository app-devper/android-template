package com.devper.template.data.remote.movie

import com.google.gson.annotations.SerializedName

data class MoviesData(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<MovieData>?,
    @SerializedName("total_results")
    val totalResults: Int,
    @SerializedName("total_pages")
    val totalPages: Int
)

