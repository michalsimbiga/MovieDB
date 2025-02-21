package com.msimbiga.moviesdb.presentation.search

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import com.msimbiga.moviesdb.presentation.models.MovieItem

@Immutable
data class SearchState(
    val isLoading: Boolean = false,
    val searchTerm: String = "",
    val likedMovies: List<Int> = emptyList(),
    val searchPagingData: PagingData<MovieItem> = PagingData.empty()
)
