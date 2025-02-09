package com.msimbiga.moviesdb.presentation.detail

sealed interface DetailsAction {
    data object OnErrorRetryClick : DetailsAction
    data object OnMovieLikeClick : DetailsAction
}