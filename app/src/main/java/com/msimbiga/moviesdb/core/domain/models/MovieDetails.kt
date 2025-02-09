package com.msimbiga.moviesdb.core.domain.models

data class MovieDetailsGenres(
    val id: Int,
    val name: String
)

data class MovieDetailsProductionCompany(
    val id: Int,
    val logoPath: String?,
    val name: String,
    val originCountry: String,
)

data class MovieDetailsProductionCountry(
    val iso: String,
    val name: String,
)

data class MovieDetails(
    val id: Int,
    val imdbId: String,
    val adult: Boolean,
    val backdropPath: String,
    val belongsToCollection: String,
    val budget: Int,
    val genres: List<MovieDetailsGenres>,
    val homepage: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val productionCompanies: List<MovieDetailsProductionCompany>,
    val productionCountries: List<MovieDetailsProductionCountry>,
    val releaseDate: String,
    val title: String,
    val revenue: Int,
    val runtime: Int,
    val status: String,
    val tagline: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
)