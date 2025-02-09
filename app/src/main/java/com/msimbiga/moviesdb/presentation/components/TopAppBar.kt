package com.msimbiga.moviesdb.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.msimbiga.moviesdb.core.presentation.theme.MoviesDBTheme

@Composable
fun TopAppBar(
    onNavigateBack: (() -> Unit)? = null,
    title: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp) // Taken from TopAppBarSmallTokens size
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),
    ) {
        if (onNavigateBack != null) {
            IconButton(
                modifier = Modifier.align(Alignment.CenterStart),
                onClick = onNavigateBack
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = title,
            fontSize = 22.sp,
            fontWeight = W500,
        )
    }
}

@Preview
@Composable
private fun TopAppBarPreview() {
    MoviesDBTheme {
        TopAppBar(
            onNavigateBack = {},
            title = "Movie Details"
        )
    }
}

@Preview
@Composable
private fun TopAppBarNoBackNavPreview() {
    MoviesDBTheme {
        TopAppBar(title = "Now Playing")
    }
}