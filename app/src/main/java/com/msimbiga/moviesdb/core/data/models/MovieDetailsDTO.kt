package com.msimbiga.moviesdb.core.data.models

import com.msimbiga.moviesdb.core.domain.models.MovieDetails
import com.msimbiga.moviesdb.core.domain.models.MovieDetailsGenres
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsGenresDTO(
    val id: Int,
    val name: String
) {
    fun toDomain() = MovieDetailsGenres(
        id = id,
        name = name
    )
}


@Serializable
data class MovieDetailsDTO(
    val id: Int,
    @SerialName("backdrop_path") val backdropPath: String?,
    @SerialName("genres") val genres: List<MovieDetailsGenresDTO>,
    @SerialName("homepage") val homepage: String,
    @SerialName("original_language") val originalLanguage: String,
    @SerialName("original_title") val originalTitle: String,
    val overview: String,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("release_date") val releaseDate: String,
    val title: String,
    val runtime: Int,
    val status: String,
    val tagline: String,
    @SerialName("vote_average") val voteAverage: Double,
) {

    fun toDomain() = MovieDetails(
        id = id,
        backdropPath = backdropPath,
        genres = genres.map(MovieDetailsGenresDTO::toDomain),
        homepage = homepage,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        runtime = runtime,
        status = status,
        tagline = tagline,
        voteAverage = voteAverage,
    )
}