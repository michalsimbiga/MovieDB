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
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,
    val posterUrl: String?,
    val posterPath: String?,
    val imageUrl: String?,
)

fun Movie.toUi() = MovieItem(
    id = id,
    adult = adult,
    posterUrl = if (posterPath != null) "https://image.tmdb.org/t/p/original$posterPath" else null,
    imageUrl = if (backdropPath != null) "https://image.tmdb.org/t/p/original$backdropPath" else null,
    genreIds = genreIds,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath,
    releaseDate = releaseDate.split("-").firstOrNull().orEmpty(),
    title = title,
    video = video,
    voteAverage = BigDecimal(voteAverage).setScale(1, RoundingMode.HALF_EVEN).toDouble(),
    voteCount = voteCount,
)