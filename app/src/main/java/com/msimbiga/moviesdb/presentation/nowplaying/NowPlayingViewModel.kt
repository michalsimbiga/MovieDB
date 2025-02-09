package com.msimbiga.moviesdb.presentation.nowplaying

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msimbiga.moviesdb.core.domain.Result
import com.msimbiga.moviesdb.core.domain.models.Movie
import com.msimbiga.moviesdb.core.domain.repository.MoviesRepository
import com.msimbiga.moviesdb.presentation.models.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NowPlayingViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val _event: Channel<NowPlayingEvent> = Channel()
    val event = _event.receiveAsFlow()

    private val _state = MutableStateFlow(NowPlayingState(isLoading = true))
    val state = _state
        .onStart { fetchNowPlayingMovies() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = _state.value
        )

    fun onAction(action: NowPlayingAction) {
        when (action) {
            is NowPlayingAction.OnMovieSelected -> {
                _event.trySend(NowPlayingEvent.NavigateToDetails(action.id))
            }

            NowPlayingAction.OnErrorRetryClicked -> fetchNowPlayingMovies()
        }
    }

    private fun fetchNowPlayingMovies(page: Int? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val movies = moviesRepository.fetchNowPlayingPage(page)) {
                is Result.Error -> {
                    _state.update {
                        it.copy(isError = true, isLoading = false)
                    }
                }

                is Result.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isError = false,
                            movies = movies.data.results.map(Movie::toUi)
                        )
                    }
                }
            }
        }
    }
}
