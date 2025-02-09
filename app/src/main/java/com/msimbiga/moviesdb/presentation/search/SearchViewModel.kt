package com.msimbiga.moviesdb.presentation.search

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class SearchViewModel : ViewModel() {

    private val _state = MutableStateFlow<SearchState>(
        SearchState(
            isLoading = true,
            searchTerm = "",
            suggestions = emptyList()
        )
    )
    val state = _state.asStateFlow()

    fun onAction(action: SearchAction) {
        when (action) {
            is SearchAction.SearchTermUpdated -> {
                _state.update { it.copy(searchTerm = action.searchTerm) }
            }
        }
    }
}
