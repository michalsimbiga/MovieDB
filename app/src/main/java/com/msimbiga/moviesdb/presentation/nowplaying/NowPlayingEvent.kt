package com.msimbiga.moviesdb.presentation.nowplaying

sealed interface NowPlayingEvent {
    data class NavigateToDetails(val id: Int) : NowPlayingEvent
}