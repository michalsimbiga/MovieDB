package com.msimbiga.moviesdb.presentation.nowplaying

sealed interface NowPlayingAction {
    data class OnMovieSelected(val id: Int) : NowPlayingAction
    data object OnErrorRetryClicked : NowPlayingAction
    data object OnGetNextPage : NowPlayingAction
}