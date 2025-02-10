package com.msimbiga.moviesdb.presentation.search

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
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val _event = Channel<SearchEvent>()
    val event = _event.receiveAsFlow()

    private val _state = MutableStateFlow<SearchState>(SearchState(isLoading = false))
    val state = _state
        .onStart {
            observeTextChanges()
            observeLikedMovies()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = _state.value
        )

    fun onAction(action: SearchAction) {
        when (action) {
            is SearchAction.SearchTermUpdated -> {
                _state.update { it.copy(searchTerm = action.searchTerm) }
            }

            is SearchAction.OnMovieClicked -> {
                _event.trySend(SearchEvent.OnMovieSelected(action.id))
            }

            is SearchAction.OnMovieLikedClicked -> {
                setMovieLiked(action.id)
            }

            SearchAction.OnLoadNextPage -> {
                getSearchPage(_state.value.page)
            }
        }
    }

    private fun observeTextChanges() {
        viewModelScope.launch(Dispatchers.IO) {
            state
                .distinctUntilChanged { old, new -> old.searchTerm == new.searchTerm }
                .map { it.searchTerm }
                .debounce(1000L)
                .collect { searchTerm ->
                    if (searchTerm.isEmpty()) return@collect
                    _state.update { it.copy(isLoading = true) }
                    getSearchPage(null)
                }
        }
    }

    private fun getSearchPage(page: Int? = null) {
        viewModelScope.launch(Dispatchers.IO) {

            when (val results =
                moviesRepository.getSearchPage(_state.value.searchTerm, page ?: FIRST_PAGE_NR)) {
                is Result.Error -> {
                    _state.update { it.copy(isLoading = false, page = FIRST_PAGE_NR) }
                }

                is Result.Success -> {
                    _state.update {
                        it.copy(
                            suggestions = it.suggestions
                                .plus(results.data.results.map(Movie::toUi))
                                .toSet()
                                .toList(),
                            isLoading = false,
                            hasMore = results.data.page < results.data.totalPages,
                            page = results.data.page.inc()
                        )
                    }
                }
            }
        }
    }

    private fun observeLikedMovies() {
        viewModelScope.launch {
            moviesRepository.getLikedMoviesFlow().collect { likedMovies ->
                _state.update { it.copy(likedMovies = likedMovies) }
            }
        }
    }

    private fun setMovieLiked(id: Int) {
        viewModelScope.launch {
            val isMovieLiked = id in _state.value.likedMovies
            moviesRepository.setMovieLiked(id, isMovieLiked.not())
        }
    }

    companion object {
        private const val FIRST_PAGE_NR = 1
    }
}
