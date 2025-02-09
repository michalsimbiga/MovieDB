package com.msimbiga.moviesdb.presentation.search

sealed interface SearchAction {
    data class SearchTermUpdated(val searchTerm: String) : SearchAction
}
