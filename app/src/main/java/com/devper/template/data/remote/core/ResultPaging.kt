package com.devper.template.data.remote.core

data class ResultPaging<T>(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<T>
)