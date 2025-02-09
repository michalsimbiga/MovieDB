package com.msimbiga.moviesdb.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun LikeButton(
    modifier: Modifier = Modifier,
    liked: Boolean = false,
    onLikeClicked: () -> Unit = {},
    hasBackground: Boolean = true,
) {
    val (enter, exit) = remember {
        arrayListOf(
            fadeIn(animationSpec = tween(durationMillis = 1500)),
            fadeOut(animationSpec = tween(durationMillis = 500))
        )
    }

    IconButton(
        onClick = onLikeClicked,
        colors = IconButtonDefaults.iconButtonColors().copy(
            containerColor = MaterialTheme.colorScheme.onBackground,
        ),
        modifier = modifier
            .size(40.dp)
            .clip(RoundedCornerShape(40.dp))
    ) {
        AnimatedVisibility(
            visible = !liked,
            enter = enter as EnterTransition,
            exit = exit as ExitTransition
        ) {
            Image(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = null,
                modifier = Modifier
            )
        }

        AnimatedVisibility(
            visible = liked,
            enter = enter,
            exit = exit
        ) {
            Image(
                imageVector = Icons.Default.Favorite,
                contentDescription = null,
                modifier = Modifier,
                colorFilter = ColorFilter.tint(Color.Red)
            )
        }
    }
}