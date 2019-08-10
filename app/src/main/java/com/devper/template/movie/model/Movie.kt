package com.devper.template.movie.model

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("adult")
    var adult: Boolean = false,
    @SerializedName("backdrop_path")
    var backdropPath: String? = null,
    @SerializedName("genres")
    var genres: List<Genre>? = null,
    @SerializedName("genre_ids")
    var genreIds: List<Int>? = null,
    @SerializedName("homepage")
    var homepage: String? = null,
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("original_language")
    var originalLanguage: String? = null,
    @SerializedName("original_title")
    var originalTitle: String? = null,
    @SerializedName("overview")
    var overview: String? = null,
    @SerializedName("popularity")
    var popularity: Float = 0.toFloat(),
    @SerializedName("poster_path")
    var posterPath: String? = null,
    @SerializedName("release_date")
    var release_date: String? = null,
    @SerializedName("revenue")
    var revenue: Long = 0,
    @SerializedName("runtime")
    var runtime: Int = 0,
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("tagline")
    var tagline: String? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("video")
    var video: Boolean = false,
    @SerializedName("vote_average")
    var voteAverage: Float = 0.toFloat(),
    @SerializedName("vote_count")
    var voteCount: Int = 0
) {
    val genresFull: String?
        get() {
            val items = genres?.map { movie -> movie.name }
            return items?.joinToString(", ")
        }

    fun getFullImageUrl(baseUrl: String): String {
        val imagePath: String? = if (posterPath != null && posterPath!!.isNotEmpty()) {
            posterPath
        } else {
            backdropPath
        }
        return baseUrl + imagePath
    }

    val runtimeFull: String
        get() {
            return runtime.toString()
        }
}