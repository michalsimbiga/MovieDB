package com.msimbiga.moviesdb.presentation.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.serialization.Serializable

@Serializable
data object SearchScreenDestination


@Composable
fun SearchScreenRoot(
    viewModel: SearchViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
}

@Composable
private fun SearchScreenContent(
    state: SearchState = SearchState(false, emptyList(), "")
) {

}
