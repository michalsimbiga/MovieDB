package com.msimbiga.moviesdb.presentation.nowplaying

import androidx.compose.runtime.Immutable
import com.msimbiga.moviesdb.presentation.models.MovieItem

@Immutable
data class NowPlayingState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val movies: List<MovieItem> = emptyList()
)

