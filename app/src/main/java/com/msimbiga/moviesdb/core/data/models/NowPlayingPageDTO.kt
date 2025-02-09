package com.msimbiga.moviesdb.core.data.models

import com.msimbiga.moviesdb.core.domain.models.NowPlayingDates
import com.msimbiga.moviesdb.core.domain.models.NowPlayingPage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NowPlayingDatesDTO(
    val maximum: String,
    val minimum: String
) {
    fun toDomain() = NowPlayingDates(
        maximum = maximum,
        minimum = minimum
    )
}

@Serializable
data class NowPlayingPageDTO(
    val dates: NowPlayingDatesDTO,
    val page: Int,
    val results: List<MovieDTO>,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int
) {
    fun toDomain() = NowPlayingPage(
        dates = dates.toDomain(),
        page = page,
        results = results.map(MovieDTO::toDomain),
        totalPages = totalPages,
        totalResults = totalResults
    )
}