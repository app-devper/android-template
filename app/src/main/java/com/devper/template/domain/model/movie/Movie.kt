package com.devper.template.domain.model.movie

data class Movie(
    val id: Int,
    val adult: Boolean,
    val backdropPath: String?,
    val genres: List<Genre>?,
    val genreIds: List<Int>?,
    val homepage: String?,
    val originalLanguage: String?,
    val originalTitle: String?,
    val overview: String?,
    val popularity: Float,
    val posterPath: String?,
    val releaseDate: String?,
    val revenue: Long,
    val runtime: Int,
    val status: String?,
    val tagLine: String?,
    val title: String?,
    val video: Boolean,
    val voteAverage: Float,
    val voteCount: Int
) {
    val genresFull: String?
        get() {
            val items = genres?.map { movie -> movie.name }
            return items?.joinToString(", ")
        }

    val runtimeFull: String
        get() {
            return runtime.toString()
        }
}