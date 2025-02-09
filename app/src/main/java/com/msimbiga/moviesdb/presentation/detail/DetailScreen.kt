package com.msimbiga.moviesdb.presentation.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                IconButton(
                    onClick = onNavigateBack
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "Movie Details",
                    fontSize = 22.sp,
                    fontWeight = W500
                )
            }
        })
    { paddingValues ->

        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            item { Text("Details ${state.movie?.id}") }
        }
    }
}