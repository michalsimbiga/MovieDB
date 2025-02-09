package com.msimbiga.moviesdb.presentation.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msimbiga.moviesdb.core.domain.Error
import com.msimbiga.moviesdb.core.domain.Result
import com.msimbiga.moviesdb.core.domain.models.Movie
import com.msimbiga.moviesdb.core.domain.models.MovieDetails
import com.msimbiga.moviesdb.core.domain.models.toMovie
import com.msimbiga.moviesdb.core.domain.repository.MoviesRepository
import com.msimbiga.moviesdb.presentation.models.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {
    private val _event = Channel<FavouritesEvent>()
    val event = _event.receiveAsFlow()

    private val _state = MutableStateFlow(FavouritesState(isLoading = true))
    val state = _state
        .onStart { loadLikedMovies() }
//        .map {
//            it.copy(
//                favouriteMovies = it.favouriteMovies
//                    .filter { movie -> movie.id in it.favouriteMoviesIdList }
//            )
//        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = _state.value
        )

    fun onAction(action: FavouritesAction) {
        when (action) {
            is FavouritesAction.OnMovieClicked -> {
                _event.trySend(FavouritesEvent.OnNavigateToDetails(action.id))
            }

            FavouritesAction.OnErrorRetryClicked -> {
                loadLikedMovies()
            }
        }
    }

    private fun loadLikedMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val movies = moviesRepository.getLikedMoviesFlow().first().map { id ->
                async { moviesRepository.getMovieDetails(id) }
            }.awaitAll()

            val loadedMovies = movies.filterIsInstance<Result.Success<MovieDetails>>()
            val hasAnyErrors = movies.filterIsInstance<Result.Error<Error>>()

            _state.update {
                it.copy(
                    isLoading = false,
                    favouriteMovies = loadedMovies
                        .map { movie -> movie.data }
                        .map(MovieDetails::toMovie)
                        .map(Movie::toUi),
                    isError = hasAnyErrors.isNotEmpty()
                )
            }
        }
    }
}
