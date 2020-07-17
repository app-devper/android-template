package com.devper.template.domain.model.user

data class Users(
    val page: Int,
    val results: List<User>,
    val total: Int,
    val totalPages: Int
)