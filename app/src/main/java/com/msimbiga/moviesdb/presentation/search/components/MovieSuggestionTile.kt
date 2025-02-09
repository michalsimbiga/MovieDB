package com.msimbiga.moviesdb.presentation.search.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.msimbiga.moviesdb.R
import com.msimbiga.moviesdb.core.domain.models.Movie
import com.msimbiga.moviesdb.presentation.models.MovieItem
import com.msimbiga.moviesdb.presentation.models.toUi

@Composable
@PreviewLightDark
fun MovieSuggestionTile(
    modifier: Modifier = Modifier,
    movie: MovieItem = Movie.mock.toUi(),
    onClick: () -> Unit = {}
) {

    Row(
        modifier = modifier
            .border(1.dp, MaterialTheme.colorScheme.onBackground, RoundedCornerShape(8.dp))
            .clickable { onClick() },
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Image
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth(0.40f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(8.dp)),
            model = ImageRequest.Builder(LocalContext.current)
                .data(movie.posterUrl)
                .crossfade(true)
                .build(),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "${movie.title} image",
            error = painterResource(R.drawable.ic_launcher_foreground),
            onError = { Log.d("VUKO", "On error $it") },
        )

        Column(
            modifier = Modifier
                .weight(1f, fill = true)
                .fillMaxHeight()
                .padding(vertical = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            //Title and tags
            Column {
                Text(
                    text = movie.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2
                )
                Text(
                    text = movie.releaseDate,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 12.sp
                )
            }

            // Rating
            Text(
                modifier = Modifier
                    .background(
                        color = Color.Gray.copy(0.3f),
                        shape = RoundedCornerShape(percent = 50)
                    )
                    .padding(horizontal = 4.dp),
                text = movie.voteAverage.toString(),
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                maxLines = 1
            )
        }
    }
}