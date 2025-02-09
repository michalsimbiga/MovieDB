package com.msimbiga.moviesdb.presentation.nowplaying

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.msimbiga.moviesdb.core.presentation.ObserveAsEvents
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
    state: NowPlayingState = NowPlayingState(movies = emptyList(), isLoading = true),
    onAction: (NowPlayingAction) -> Unit = {}
) {
    // TODO: Add Error and loading views

    if (state.movies.isNotEmpty()) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 180.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(state.movies) { movie ->
                MovieTile(
                    movie = movie,
                    onClick = { onAction(NowPlayingAction.OnMovieSelected(movie.id)) }
                )
            }
        }

    }

}