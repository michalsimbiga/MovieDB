package com.msimbiga.moviesdb.presentation.search

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.msimbiga.moviesdb.core.presentation.ObserveAsEvents
import com.msimbiga.moviesdb.presentation.components.DefaultLoadingView
import com.msimbiga.moviesdb.presentation.search.components.MovieSuggestionTile
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
    state: SearchState = SearchState(false, emptyList(), ""),
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

        LazyColumn(
            modifier = Modifier.padding(horizontal = 12.dp),
            contentPadding = paddingValues,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (state.isLoading) {
                item { DefaultLoadingView() }
            } else {
                items(state.suggestions, key = { movie -> movie.id }) { movie ->
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
                if (state.hasMore) {
                    item {
                        LaunchedEffect(state.page) {
                            onAction(SearchAction.OnLoadNextPage)
                        }
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
