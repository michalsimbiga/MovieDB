package com.msimbiga.moviesdb.core.domain.models

data class SearchPage(
    val page: Int,
    val results: List<Movie>,
    val totalPages: Int,
    val totalResults: Int
)
