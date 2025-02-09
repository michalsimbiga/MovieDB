package com.msimbiga.moviesdb.presentation.nowplaying.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
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
            .border(1.dp, Color.Blue)
            .clickable { onClick() }
    ) {
        // Image
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Red),
            model = movie.posterUrl,
            contentScale = ContentScale.Crop,
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
                text = movie.title
            )

            Text(
                modifier = Modifier
                    .background(
                        color = Color.Gray.copy(0.3f),
                        shape = RoundedCornerShape(percent = 50)
                    )
                    .padding(horizontal = 4.dp, vertical = 2.dp),
                text = movie.voteAverage.toString(),
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
        }
    }
}