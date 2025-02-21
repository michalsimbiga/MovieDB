package com.msimbiga.moviesdb.presentation.search

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.msimbiga.moviesdb.core.presentation.ObserveAsEvents
import com.msimbiga.moviesdb.presentation.components.DefaultLoadingView
import com.msimbiga.moviesdb.presentation.search.components.MovieSuggestionTile
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.Serializable

@Serializable
data object SearchScreenDestination


@Composable
fun SearchScreenRoot(
    viewModel: SearchViewModel = hiltViewModel(),
    onNavigateToDetails: (Int) -> Unit = {}
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.event) { event ->
        when (event) {
            is SearchEvent.OnMovieSelected -> {
                onNavigateToDetails(event.movieId)
            }
        }
    }

    SearchScreenContent(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun SearchScreenContent(
    state: SearchState = SearchState(false),
    onAction: (SearchAction) -> Unit = {}
) {
    val focusRequester = FocusRequester.Companion.FocusRequesterFactory.component1()

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 24.dp, vertical = 12.dp)
                    .clickable(enabled = true, onClick = { focusRequester.requestFocus() })
                    .border(1.dp, MaterialTheme.colorScheme.onBackground, RoundedCornerShape(16.dp))
                    .padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
                BasicTextField(
                    modifier = Modifier
                        .weight(1f, fill = true)
                        .focusRequester(focusRequester),
                    value = state.searchTerm,
                    singleLine = true,
                    textStyle = TextStyle(
                        color = MaterialTheme.colorScheme.onBackground,
                    ),
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
                    onValueChange = { value -> onAction(SearchAction.SearchTermUpdated(value)) },
                )
            }
        }
    ) { paddingValues ->

        val searchPagingData = remember(state.searchPagingData) {
            flow { emit(state.searchPagingData) }
        }.collectAsLazyPagingItems()

        LazyColumn(
            modifier = Modifier.padding(horizontal = 12.dp),
            contentPadding = paddingValues,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            if (state.isLoading) {
                item { DefaultLoadingView() }
            } else {

                items(
                    count = searchPagingData.itemCount,
                    key = { movie -> movie }
                ) { index ->
                    val movie = checkNotNull(searchPagingData[index])
                    MovieSuggestionTile(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1.9f),
                        movie = movie,
                        onClick = { onAction(SearchAction.OnMovieClicked(movie.id)) },
                        isLiked = movie.id in state.likedMovies,
                        onLikeClick = { onAction(SearchAction.OnMovieLikedClicked(movie.id)) }
                    )
                }

                // Visible for initial load of the paginated data
                if (searchPagingData.loadState.refresh == LoadState.Loading) {
                    item {
                        Text(
                            text = "Loading data",
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                // Visible loading for appending new data to existing list
                if (searchPagingData.loadState.append == LoadState.Loading) {
                    Log.d("VUKO", "is loading")
                    item {
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

                // Visible when fetching initial/appending has failed
                if (searchPagingData.loadState.hasError) {
                    Log.d("VUKO", "is error")
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(onClick = { searchPagingData.retry() }) {
                                Text("Retry loading data")
                            }
                        }
                    }
                }
            }
        }
    }
}
