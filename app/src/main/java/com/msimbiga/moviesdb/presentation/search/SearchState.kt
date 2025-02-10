package com.msimbiga.moviesdb.presentation.search

import androidx.compose.runtime.Immutable
import com.msimbiga.moviesdb.presentation.models.MovieItem

@Immutable
data class SearchState(
    val isLoading: Boolean = false,
    val suggestions: List<MovieItem> = emptyList(),
    val searchTerm: String = "",
    val likedMovies: List<Int> = emptyList(),
    val page: Int = 0,
    val hasMore: Boolean = false
)
