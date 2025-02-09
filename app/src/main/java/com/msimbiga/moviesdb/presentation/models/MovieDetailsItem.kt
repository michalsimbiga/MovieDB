package com.msimbiga.moviesdb.presentation.models

import com.msimbiga.moviesdb.core.domain.models.MovieDetails
import com.msimbiga.moviesdb.core.domain.models.MovieDetailsGenres
import com.msimbiga.moviesdb.core.domain.models.MovieDetailsProductionCompany
import com.msimbiga.moviesdb.core.domain.models.MovieDetailsProductionCountry
import java.math.BigDecimal
import java.math.RoundingMode


data class MovieDetailsItem(
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
    val voteCount: Int,
    val posterUrl: String,
    val imageUrl: String,
)

fun MovieDetails.toUi() = MovieDetailsItem(
    id = id,
    adult = adult,
    posterUrl = "https://image.tmdb.org/t/p/original$posterPath",
    imageUrl = "https://image.tmdb.org/t/p/original$backdropPath",
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
    imdbId = imdbId,
    backdropPath = backdropPath,
    belongsToCollection = belongsToCollection,
    budget = budget,
    genres = genres,
    homepage = homepage,
    productionCompanies = productionCompanies,
    productionCountries = productionCountries,
    revenue = revenue,
    runtime = runtime,
    status = status,
    tagline = tagline,
)