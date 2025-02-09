package com.msimbiga.moviesdb.core.data.models

import com.msimbiga.moviesdb.core.domain.models.Movie
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDTO(
    val id: Int,
    @SerialName("backdrop_path") val backdropPath: String?,
    val overview: String,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("release_date") val releaseDate: String,
    val title: String,
    @SerialName("vote_average") val voteAverage: Double,
) {
    fun toDomain(): Movie =
        Movie(
            id = id,
            backdropPath = backdropPath,
            overview = overview,
            posterPath = posterPath,
            releaseDate = releaseDate,
            title = title,
            voteAverage = voteAverage,
        )
}