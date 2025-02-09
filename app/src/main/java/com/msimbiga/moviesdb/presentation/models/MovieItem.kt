package com.msimbiga.moviesdb.presentation.models

import com.msimbiga.moviesdb.core.domain.models.Movie
import java.math.BigDecimal
import java.math.RoundingMode

data class MovieItem(
    val id: Int,
    val overview: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
    val posterUrl: String?,
    val posterPath: String?,
    val imageUrl: String?,
)

fun Movie.toUi() = MovieItem(
    id = id,
    posterUrl = if (posterPath != null) "https://image.tmdb.org/t/p/original$posterPath" else null,
    imageUrl = if (backdropPath != null) "https://image.tmdb.org/t/p/original$backdropPath" else null,
    overview = overview,
    posterPath = posterPath,
    releaseDate = releaseDate.split("-").firstOrNull().orEmpty(),
    title = title,
    voteAverage = BigDecimal(voteAverage).setScale(1, RoundingMode.HALF_EVEN).toDouble(),
)