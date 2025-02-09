package com.msimbiga.moviesdb.core.domain.models

data class MovieDetailsGenres(
    val id: Int,
    val name: String
)


data class MovieDetails(
    val id: Int,
    val backdropPath: String?,
    val genres: List<MovieDetailsGenres>,
    val homepage: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val posterPath: String?,
    val releaseDate: String,
    val title: String,
    val runtime: Int,
    val status: String,
    val tagline: String,
    val voteAverage: Double,
)