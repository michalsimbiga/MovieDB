package com.msimbiga.moviesdb.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msimbiga.moviesdb.core.domain.Result
import com.msimbiga.moviesdb.core.domain.repository.MoviesRepository
import com.msimbiga.moviesdb.presentation.models.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val moviesRepository: MoviesRepository,
) : ViewModel() {

    private val selectedId = savedStateHandle.get<Int?>("id")

    private val _state = MutableStateFlow<DetailState>(DetailState())
    val state = combine(
        _state,
        moviesRepository.getLikedMoviesFlow()
    ) { state, likedList ->
        state.copy(likedMovies = likedList)
    }
        .onStart { fetchMovieDetails() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = _state.value
        )

    fun onAction(action: DetailsAction) {
        when(action){
            DetailsAction.OnErrorRetryClick -> fetchMovieDetails()
            DetailsAction.OnMovieLikeClick -> onMovieLiked()
        }
    }

    private fun fetchMovieDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val details = moviesRepository.getMovieDetails(checkNotNull(selectedId))) {
                is Result.Error -> {
                    _state.update { it.copy(isError = true) }
                }

                is Result.Success -> {
                    _state.getAndUpdate { state -> state.copy(movie = details.data.toUi()) }
                }
            }
        }
    }

    private fun onMovieLiked() {
        viewModelScope.launch(Dispatchers.IO) {
            val movieId = checkNotNull(state.value.movie?.id)
            val isLiked = checkNotNull(movieId in state.value.likedMovies)
            moviesRepository.setMovieLiked(movieId, isLiked.not())
        }
    }
}