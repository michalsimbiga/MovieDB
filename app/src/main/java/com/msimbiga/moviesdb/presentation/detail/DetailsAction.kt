package com.msimbiga.moviesdb.presentation.detail

sealed interface DetailsAction {
    data object OnFavouritesClick : DetailsAction
}