package com.devper.template.domain.model.movie

data class Movies(
    val page: Int,
    val results: List<Movie>?,
    val totalResults: Int,
    val totalPages: Int
)

