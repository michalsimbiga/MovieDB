package com.msimbiga.moviesdb.presentation.nowplaying

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.serialization.Serializable

@Serializable
data object NowPlayingDestination

@Composable
fun NowPlayingScreenRoot(
    viewModel: NowPlayingViewModel = hiltViewModel(),
    onNavigateToDetail: (Int) -> Unit = {},
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    NowPlayingScreenContent(
        state = state
    )
}

@Composable
@Preview
fun NowPlayingScreenContent(
    state: NowPlayingState = NowPlayingState(movies = emptyList(), isLoading = true),
    onAction: (NowPlayingAction) -> Unit = {}
) {

}