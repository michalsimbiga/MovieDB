package com.msimbiga.moviesdb.core.domain.models

data class Movie(
    val id: Int,
    val adult: Boolean,
    val backdropPath: String,
    val genreIds: List<Int>,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
) {

    companion object {
        val mock = Movie(
            id = 939243,
            adult = false,
            backdropPath = "/zOpe0eHsq0A2NvNyBbtT6sj53qV.jpg",
            genreIds = listOf(28, 878, 35, 10751),
            originalLanguage = "en",
            originalTitle = "Sonic the Hedgehog 3",
            overview = "Sonic, Knuckles, and Tails reunite against a powerful new adversary, Shadow, a mysterious villain with powers unlike anything they have faced before. With their abilities outmatched in every way, Team Sonic must seek out an unlikely alliance in hopes of stopping Shadow and protecting the planet.",
            popularity = 4014.717,
            posterPath = "/d8Ryb8AunYAuycVKDp5HpdWPKgC.jpg",
            releaseDate = "2024-12-19",
            title = "Sonic the Hedgehog 3",
            video = false,
            voteAverage = 7.776,
            voteCount = 1610
        )
    }
}
