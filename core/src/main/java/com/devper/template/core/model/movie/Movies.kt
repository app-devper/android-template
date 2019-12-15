package com.devper.template.core.model.movie

import com.devper.template.core.model.movie.Movie

data class Movies(
    val page: Int,
    val results: List<Movie>?,
    val totalResults: Int,
    val totalPages: Int
)

