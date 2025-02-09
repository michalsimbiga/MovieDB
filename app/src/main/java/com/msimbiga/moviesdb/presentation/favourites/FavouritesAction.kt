package com.msimbiga.moviesdb.presentation.favourites

sealed interface FavouritesAction {
    data class OnMovieClicked(val id: Int) : FavouritesAction
    data object OnErrorRetryClicked : FavouritesAction

}
