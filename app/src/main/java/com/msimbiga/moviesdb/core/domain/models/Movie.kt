package com.msimbiga.moviesdb.core.domain.models

data class Movie(
    val id: Int,
    val backdropPath: String?,
    val overview: String,
    val posterPath: String?,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
) {

    companion object {
        val mock = Movie(
            id = 939243,
            backdropPath = "/zOpe0eHsq0A2NvNyBbtT6sj53qV.jpg",
            overview = "Sonic, Knuckles, and Tails reunite against a powerful new adversary, Shadow, a mysterious villain with powers unlike anything they have faced before. With their abilities outmatched in every way, Team Sonic must seek out an unlikely alliance in hopes of stopping Shadow and protecting the planet.",
            posterPath = "/d8Ryb8AunYAuycVKDp5HpdWPKgC.jpg",
            releaseDate = "2024-12-19",
            title = "Sonic the Hedgehog 3",
            voteAverage = 7.776,
        )
    }
}
