package com.msimbiga.moviesdb.core.domain.models

data class NowPlayingDates(
    val maximum: String,
    val minimum: String
)

data class NowPlayingPage(
    val dates: NowPlayingDates,
    val page: Int,
    val results: List<Movie>,
    val totalPages: Int,
    val totalResults: Int
)
