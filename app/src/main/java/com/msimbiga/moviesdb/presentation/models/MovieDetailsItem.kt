package com.msimbiga.moviesdb.presentation.models

import com.msimbiga.moviesdb.core.domain.models.MovieDetails
import com.msimbiga.moviesdb.core.domain.models.MovieDetailsGenres
import java.math.BigDecimal
import java.math.RoundingMode


data class MovieDetailsItem(
    val id: Int,
    val backdropPath: String?,
    val genres: List<MovieDetailsGenres>,
    val homepage: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val posterPath: String?,
    val releaseDate: String, // only the year of release
    val title: String,
    val runtime: Int,
    val status: String,
    val tagline: String,
    val voteAverage: Double,
    val posterUrl: String?,
    val imageUrl: String?,
)

fun MovieDetails.toUi() = MovieDetailsItem(
    id = id,
    posterUrl = if (posterPath != null) "https://image.tmdb.org/t/p/original$posterPath" else null,
    imageUrl = if (backdropPath != null) "https://image.tmdb.org/t/p/original$backdropPath" else null,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview,
    posterPath = posterPath,
    releaseDate = releaseDate.split("-").firstOrNull()
        .orEmpty(), // Would be better to parse date as DateTime and get the year from it
    title = title,
    voteAverage = BigDecimal(voteAverage).setScale(1, RoundingMode.HALF_EVEN)
        .toDouble(), // To round votes to singular decimal place
    backdropPath = backdropPath,
    genres = genres,
    homepage = homepage,
    runtime = runtime,
    status = status,
    tagline = tagline,
)