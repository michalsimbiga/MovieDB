package com.msimbiga.moviesdb.presentation.favourites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.msimbiga.moviesdb.core.presentation.ObserveAsEvents
import com.msimbiga.moviesdb.presentation.components.DefaultErrorView
import com.msimbiga.moviesdb.presentation.components.DefaultLoadingView
import com.msimbiga.moviesdb.presentation.components.TopAppBar
import com.msimbiga.moviesdb.presentation.nowplaying.components.MovieTile
import kotlinx.serialization.Serializable

@Serializable
data object FavouritesDestination


@Composable
fun FavouritesScreenRoot(
    viewModel: FavouritesViewModel = hiltViewModel(),
    onNavigateToDetails: (Int) -> Unit = {},
    onNavigateBackClicked: () -> Unit = {}
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.event) { event ->
        when (event) {
            is FavouritesEvent.OnNavigateToDetails -> {
                onNavigateToDetails(event.id)
            }
        }
    }

    FavouritesScreenContent(
        state = state,
        onNavigateBackClicked = onNavigateBackClicked,
        onAction = viewModel::onAction
    )
}


@Composable
private fun FavouritesScreenContent(
    state: FavouritesState = FavouritesState(
        favouriteMovies = emptyList(),
        favouriteMoviesIdList = emptyList()
    ),
    onAction: (FavouritesAction) -> Unit = {},
    onNavigateBackClicked: () -> Unit
) {

    Scaffold(
        topBar = { TopAppBar(title = "Favourites", onNavigateBack = onNavigateBackClicked) }
    ) { paddingValues ->

        when {
            state.isError -> {
                DefaultErrorView(onRetryClick = { onAction(FavouritesAction.OnErrorRetryClicked) })
            }

            state.isLoading -> {
                DefaultLoadingView()
            }

            state.favouriteMovies.isNotEmpty() -> {
                val scrollState = rememberLazyGridState()

                LazyVerticalGrid(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    state = scrollState,
                    columns = GridCells.Adaptive(minSize = 168.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = paddingValues
                ) {
                    items(state.favouriteMovies, key = { movie -> movie.id }) { movie ->
                        MovieTile(
                            movie = movie,
                            onClick = { onAction(FavouritesAction.OnMovieClicked(movie.id)) }
                        )
                    }
                }
            }

            state.favouriteMovies.isEmpty() -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text("No favourite movies yet")
                }
            }
        }
    }
}
