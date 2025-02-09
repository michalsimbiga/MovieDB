package com.msimbiga.moviesdb.presentation.detail

import com.msimbiga.moviesdb.presentation.models.MovieDetailsItem

sealed interface DetailState {
    data object Loading : DetailState
    data object Error : DetailState
    data class Success(val movie: MovieDetailsItem) : DetailState
}
