package com.msimbiga.moviesdb.core.data.models

import com.msimbiga.moviesdb.core.domain.models.SearchPage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class SearchPageDTO(
    val page: Int,
    val results: List<MovieDTO>,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int
) {
    fun toDomain() = SearchPage(
        page = page,
        results = results.map(MovieDTO::toDomain),
        totalPages = totalPages,
        totalResults = totalResults
    )
}
