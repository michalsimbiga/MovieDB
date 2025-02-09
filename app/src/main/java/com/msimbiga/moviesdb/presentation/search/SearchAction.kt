package com.msimbiga.moviesdb.presentation.search

sealed interface SearchAction {
    data class SearchTermUpdated(val searchTerm: String) : SearchAction
    data class OnMovieClicked(val id: Int) : SearchAction
}
