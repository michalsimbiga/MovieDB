package com.msimbiga.moviesdb.presentation.models

import com.msimbiga.moviesdb.core.domain.models.Movie
import java.math.BigDecimal
import java.math.RoundingMode

data class MovieItem(
    val id: Int,
    val adult: Boolean,
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
    val voteCount: Int,
    val posterUrl: String,
    val imageUrl: String,
)

fun Movie.toUi() = MovieItem(
    id = id,
    adult = adult,
    posterUrl = "https://image.tmdb.org/t/p/original$posterPath",
    imageUrl = "https://image.tmdb.org/t/p/original$backdropPath",
    genreIds = genreIds,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath,
    releaseDate = releaseDate,
    title = title,
    video = video,
    voteAverage = BigDecimal(voteAverage).setScale(1, RoundingMode.HALF_EVEN).toDouble(),
    voteCount = voteCount,
)