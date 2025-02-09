package com.msimbiga.moviesdb.presentation.favourites

sealed interface FavouritesEvent {
    data class OnNavigateToDetails(val id: Int) : FavouritesEvent
}
