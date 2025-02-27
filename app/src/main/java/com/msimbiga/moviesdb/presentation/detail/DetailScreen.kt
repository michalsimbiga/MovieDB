package com.msimbiga.moviesdb.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.msimbiga.moviesdb.R
import com.msimbiga.moviesdb.presentation.components.DefaultErrorView
import com.msimbiga.moviesdb.presentation.components.DefaultLoadingView
import com.msimbiga.moviesdb.presentation.components.LikeButton
import com.msimbiga.moviesdb.presentation.components.TopAppBar
import kotlinx.serialization.Serializable

@Serializable
data class DetailScreenDestination(val id: Int)

@Composable
fun DetailScreenRoot(
    viewModel: DetailViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {},
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    DetailScreenContent(
        state = state,
        onNavigateBack = onNavigateBack,
        onAction = viewModel::onAction
    )
}

@Composable
fun DetailScreenContent(
    state: DetailState = DetailState(),
    onNavigateBack: () -> Unit = {},
    onAction: (DetailsAction) -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = stringResource(R.string.movie_details_title),
                onNavigateBack = onNavigateBack
            )
        })
    { paddingValues ->

        when {
            state.isError ->
                DefaultErrorView(onRetryClick = { onAction(DetailsAction.OnErrorRetryClick) })

            state.isLoading -> {
                DefaultLoadingView()
            }

            state.movie != null ->
                LazyColumn(
                    modifier = Modifier.padding(paddingValues),
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 36.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1.6f)
                                .clip(RoundedCornerShape(16.dp))
                                .border(1.dp, Color.White, shape = RoundedCornerShape(16.dp)),
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(state.movie.imageUrl)
                                .crossfade(true)
                                .build(),
                            contentScale = ContentScale.Crop,
                            contentDescription = "${state.movie.title} image",
                            placeholder = painterResource(R.drawable.ic_launcher_foreground),
                            error = painterResource(R.drawable.ic_launcher_foreground),
                        )
                    }

                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {

                            Text(
                                modifier = Modifier.weight(1f, fill = true),
                                text = state.movie.title,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )

                            LikeButton(
                                modifier = Modifier,
                                liked = state.movie.id in state.likedMovies,
                                onLikeClicked = { onAction(DetailsAction.OnMovieLikeClick) }
                            )
                        }
                    }

                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            state.movie.releaseDate.let {
                                InfoChip(it)
                            }
                            state.movie.runtime.let {
                                InfoChip(stringResource(R.string.movie_details_runtime, it))
                            }
                        }

                    }

                    item { Text(state.movie.overview) }

                    item {
                        OverallRating(
                            modifier = Modifier.fillMaxWidth(),
                            voteAverage = state.movie.voteAverage
                        )
                    }
                }
        }
    }
}


@Composable
@PreviewLightDark
private fun InfoChip(text: String = "2024") {
    Box(
        modifier = Modifier
            .background(
                MaterialTheme.colorScheme.primary,
                RoundedCornerShape(16.dp)
            )
            .border(1.dp, MaterialTheme.colorScheme.onPrimary, RoundedCornerShape(16.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
private fun OverallRating(modifier: Modifier, voteAverage: Double) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.movie_details_overall_rating_text))
        Text(
            text = "$voteAverage",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
    }
}
