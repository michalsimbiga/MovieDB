package com.msimbiga.moviesdb.presentation.search

sealed interface SearchEvent {
    data class OnMovieSelected(val movieId: Int) : SearchEvent
}
