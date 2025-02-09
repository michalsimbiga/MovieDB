package com.msimbiga.moviesdb.presentation.search

import androidx.compose.runtime.Immutable
import com.msimbiga.moviesdb.presentation.models.MovieItem

@Immutable
data class SearchState(
    val isLoading: Boolean,
    val suggestions: List<MovieItem>,
    val searchTerm: String = "",
    val likedMovies: List<Int> = emptyList()
)
