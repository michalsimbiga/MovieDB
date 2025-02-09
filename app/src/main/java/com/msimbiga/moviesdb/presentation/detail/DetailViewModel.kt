package com.msimbiga.moviesdb.presentation.detail

import android.util.Log
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
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val moviesRepository: MoviesRepository,
) : ViewModel() {

    private val selectedId = savedStateHandle.get<Int?>("id")

    private val _state = MutableStateFlow(DetailState())
    val state = _state
        .onStart {
            fetchMovieDetails()
            Log.d("VUKO", "Selected id is $selectedId")
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = _state.value
        )

    fun onAction(action: DetailsAction) {
        when (action) {
            DetailsAction.OnFavouritesClick -> TODO()
        }
    }

    private fun fetchMovieDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val details = moviesRepository.getMovieDetails(checkNotNull(selectedId))) {
                is Result.Error -> {}
                is Result.Success -> {
                    _state.update { it.copy(movie = details.data.toUi()) }
                }
            }
        }
    }
}