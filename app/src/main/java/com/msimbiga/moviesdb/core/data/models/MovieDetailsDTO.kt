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
    @SerialName("imdb_id") val imdbId: String,
    val adult: Boolean,
    @SerialName("backdrop_path") val backdropPath: String?,
    @SerialName("budget") val budget: Int,
    @SerialName("genres") val genres: List<MovieDetailsGenresDTO>,
    @SerialName("homepage") val homepage: String,
    @SerialName("original_language") val originalLanguage: String,
    @SerialName("original_title") val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("release_date") val releaseDate: String,
    val title: String,
    val revenue: Int,
    val runtime: Int,
    val status: String,
    val tagline: String,
    val video: Boolean,
    @SerialName("vote_average") val voteAverage: Double,
    @SerialName("vote_count") val voteCount: Int
) {

    fun toDomain() = MovieDetails(
        id = id,
        imdbId = imdbId,
        adult = adult,
        backdropPath = backdropPath,
        belongsToCollection = "asd",
        budget = budget,
        genres = genres.map(MovieDetailsGenresDTO::toDomain),
        homepage = homepage,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        revenue = revenue,
        runtime = runtime,
        status = status,
        tagline = tagline,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount
    )
}