package com.msimbiga.moviesdb.presentation.search

import com.msimbiga.moviesdb.presentation.models.MovieItem

data class SearchState(
    val isLoading: Boolean,
    val suggestions: List<MovieItem>,
    val searchTerm: String = ""
)
