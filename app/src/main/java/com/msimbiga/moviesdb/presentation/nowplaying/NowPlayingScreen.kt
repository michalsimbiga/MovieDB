package com.msimbiga.moviesdb.presentation.nowplaying

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.msimbiga.moviesdb.R
import com.msimbiga.moviesdb.core.presentation.ObserveAsEvents
import com.msimbiga.moviesdb.presentation.components.DefaultErrorView
import com.msimbiga.moviesdb.presentation.components.DefaultLoadingView
import com.msimbiga.moviesdb.presentation.components.TopAppBar
import com.msimbiga.moviesdb.presentation.nowplaying.components.MovieTile
import kotlinx.coroutines.flow.flow
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
    Scaffold(
        topBar = { TopAppBar(title = stringResource(R.string.now_playing_title)) }
    ) { paddingValues ->

        when (state) {
            is NowPlayingState.Error -> {
                DefaultErrorView(onRetryClick = { onAction(NowPlayingAction.OnErrorRetryClicked) })
            }

            is NowPlayingState.Loading -> {
                DefaultLoadingView()
            }

            is NowPlayingState.Success -> {
                val scrollState = rememberLazyGridState()

                val pagingItems = remember(state.moviesPagingData) {
                    flow { emit(state.moviesPagingData) }
                }.collectAsLazyPagingItems()

                LazyVerticalGrid(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    state = scrollState,
                    columns = GridCells.Adaptive(minSize = 168.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = paddingValues
                ) {

                    items(
                        count = pagingItems.itemCount,
                        key = { item -> item.toString() }
                    ) { index ->
                        val movie = checkNotNull(pagingItems.get(index))
                        MovieTile(
                            movie = movie,
                            onClick = { onAction(NowPlayingAction.OnMovieSelected(movie.id)) }
                        )
                    }

                    if (pagingItems.loadState.refresh is LoadState.Loading) {
                        // TODO : Visible loading state on scroll down
                        Log.d("VUKO", "is loading")
                        item(
//                            span = { GridItemSpan(maxLineSpan) }
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(1f),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }
            }
        }
    }
}