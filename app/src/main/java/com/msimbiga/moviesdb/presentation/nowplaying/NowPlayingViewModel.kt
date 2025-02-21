package com.msimbiga.moviesdb.presentation.nowplaying

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.msimbiga.moviesdb.core.domain.models.Movie
import com.msimbiga.moviesdb.core.domain.repository.MoviesRepository
import com.msimbiga.moviesdb.presentation.models.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
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

    private val _state = MutableStateFlow<NowPlayingState>(NowPlayingState.Loading)
    val state = _state
        .onStart { startPagingData() }
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

            NowPlayingAction.OnErrorRetryClicked -> startPagingData()
        }
    }

    private fun startPagingData() {
        viewModelScope.launch(Dispatchers.IO) {
            moviesRepository.getNowPlayingPagingData()
                .cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    Log.d("VUKO", "paging result $pagingData")
                    _state.update {
                        NowPlayingState.Success(moviesPagingData = pagingData.map(Movie::toUi))
                    }
                }
        }
    }
}
