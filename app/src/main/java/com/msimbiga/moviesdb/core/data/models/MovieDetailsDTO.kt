package com.msimbiga.moviesdb.core.data.models

import com.msimbiga.moviesdb.core.domain.models.MovieDetails
import com.msimbiga.moviesdb.core.domain.models.MovieDetailsGenres
import com.msimbiga.moviesdb.core.domain.models.MovieDetailsProductionCompany
import com.msimbiga.moviesdb.core.domain.models.MovieDetailsProductionCountry
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
data class MovieDetailsProductionCompaniesDTO(
    val id: Int,
    @SerialName("logo_path") val logoPath: String?,
    val name: String,
    @SerialName("origin_country") val originCountry: String,
) {
    fun toDomain() = MovieDetailsProductionCompany(
        id = id,
        logoPath = logoPath,
        name = name,
        originCountry = originCountry
    )
}

@Serializable
data class MovieDetailsProductionCountryDTO(
    @SerialName("iso_3166_1") val iso: String,
    val name: String,
) {
    fun toDomain() = MovieDetailsProductionCountry(
        iso = iso,
        name = name
    )
}

@Serializable
data class MovieDetailsDTO(
    val id: Int,
    @SerialName("imdb_id") val imdbId: String,
    val adult: Boolean,
    @SerialName("backdrop_path") val backdropPath: String,
//    @SerialName("belongs_to_collection") val belongsToCollection: String,
    @SerialName("budget") val budget: Int,
    @SerialName("genres") val genres: List<MovieDetailsGenresDTO>,
    @SerialName("homepage") val homepage: String,
    @SerialName("original_language") val originalLanguage: String,
    @SerialName("original_title") val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerialName("poster_path") val posterPath: String,
    @SerialName("production_companies") val productionCompanies: List<MovieDetailsProductionCompaniesDTO>,
    @SerialName("production_countries") val productionCountries: List<MovieDetailsProductionCountryDTO>,
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
        productionCompanies = productionCompanies.map(MovieDetailsProductionCompaniesDTO::toDomain),
        productionCountries = productionCountries.map(MovieDetailsProductionCountryDTO::toDomain),
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