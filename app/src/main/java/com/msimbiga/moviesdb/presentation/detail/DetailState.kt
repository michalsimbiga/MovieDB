package com.msimbiga.moviesdb.presentation.detail

import androidx.compose.runtime.Immutable
import com.msimbiga.moviesdb.presentation.models.MovieDetailsItem

@Immutable
data class DetailState(
    val movie: MovieDetailsItem? = null,
    val likedMovies: List<Int> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false
)
