package com.msimbiga.moviesdb.presentation.nowplaying.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
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
@Preview(showBackground = true)
fun MovieTile(
    movie: MovieItem = Movie.mock.toUi(),
    onClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .padding(bottom = 12.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
    ) {
        // Image
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.6f)
                .clip(RoundedCornerShape(8.dp)),
            model = ImageRequest.Builder(LocalContext.current)
                .data(movie.posterUrl)
                .crossfade(true)
                .build(),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "${movie.title} image",
            onError = { Log.d("VUKO", "On error $it") },
        )

        // Name + rating
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = movie.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

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