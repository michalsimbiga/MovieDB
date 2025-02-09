package com.msimbiga.moviesdb.presentation.nowplaying

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.msimbiga.moviesdb.core.presentation.ObserveAsEvents
import com.msimbiga.moviesdb.presentation.components.DefaultErrorView
import com.msimbiga.moviesdb.presentation.components.TopAppBar
import com.msimbiga.moviesdb.presentation.nowplaying.components.MovieTile
import kotlinx.serialization.Serializable

@Serializable
data object NowPlayingDestination

@Composable
fun NowPlayingScreenRoot(
    viewModel: NowPlayingViewModel = hiltViewModel(),
    onNavigateToDetail: (Int) -> Unit = {},
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.event) { event ->
        when (event) {
            is NowPlayingEvent.NavigateToDetails -> onNavigateToDetail(event.id)
        }
    }

    NowPlayingScreenContent(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
@Preview
fun NowPlayingScreenContent(
    state: NowPlayingState = NowPlayingState.Loading,
    onAction: (NowPlayingAction) -> Unit = {}
) {
    // TODO: Add Error and loading views

    Scaffold(
        topBar = { TopAppBar(title = "Now playing") }
    ) { paddingValues ->

        when (state) {
            is NowPlayingState.Error -> {
                DefaultErrorView(
                    onRetryClick = { onAction(NowPlayingAction.OnErrorRetryClicked) }
                )
            }

            NowPlayingState.Loading -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator()
                }
            }

            is NowPlayingState.Success -> {
                val scrollState = rememberLazyGridState()

                LazyVerticalGrid(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    state = scrollState,
                    columns = GridCells.Adaptive(minSize = 168.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = paddingValues
                ) {
                    items(state.movies, key = { movie -> movie.id }) { movie ->
                        MovieTile(
                            movie = movie,
                            onClick = { onAction(NowPlayingAction.OnMovieSelected(movie.id)) }
                        )
                    }
                }
            }
        }
    }
}