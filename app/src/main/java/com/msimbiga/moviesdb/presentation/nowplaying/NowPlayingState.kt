package com.msimbiga.moviesdb.presentation.nowplaying

import com.msimbiga.moviesdb.presentation.models.MovieItem

sealed interface NowPlayingState {
    data object Loading : NowPlayingState
    data class Error(val movies: List<MovieItem>) : NowPlayingState
    data class Success(val movies: List<MovieItem>) : NowPlayingState
}
