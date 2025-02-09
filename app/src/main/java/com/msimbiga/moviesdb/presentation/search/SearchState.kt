package com.msimbiga.moviesdb.presentation.search

import com.msimbiga.moviesdb.core.domain.models.Movie

data class SearchState(
    val isLoading: Boolean,
    val suggestions: List<Movie>,
    val searchTerm: String = ""
)
