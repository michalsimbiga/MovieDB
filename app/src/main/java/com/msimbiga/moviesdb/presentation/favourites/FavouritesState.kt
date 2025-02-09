package com.msimbiga.moviesdb.presentation.favourites

import androidx.compose.runtime.Immutable
import com.msimbiga.moviesdb.presentation.models.MovieItem

@Immutable
data class FavouritesState(
    val favouriteMoviesIdList: List<Int> = emptyList(),
    val favouriteMovies: List<MovieItem> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false
)
