package com.msimbiga.moviesdb.presentation.nowplaying

import com.msimbiga.moviesdb.presentation.models.MovieItem

data class NowPlayingState(
    val movies: List<MovieItem>,
    val isLoading: Boolean
)