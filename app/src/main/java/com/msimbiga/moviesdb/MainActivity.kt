package com.msimbiga.moviesdb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.msimbiga.moviesdb.core.presentation.theme.MoviesDBTheme
import com.msimbiga.moviesdb.presentation.NavigationGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoviesDBTheme {
                NavigationGraph()
            }
        }
    }
}
