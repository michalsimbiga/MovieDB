package com.msimbiga.moviesdb.presentation.nowplaying

data class NowPlayingState(
    val movies: List<String>,
    val isLoading: Boolean
)