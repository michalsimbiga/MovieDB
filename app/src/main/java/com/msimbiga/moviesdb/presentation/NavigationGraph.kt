package com.msimbiga.moviesdb.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.msimbiga.moviesdb.presentation.detail.DetailScreenDestination
import com.msimbiga.moviesdb.presentation.detail.DetailScreenRoot
import com.msimbiga.moviesdb.presentation.nowplaying.NowPlayingDestination
import com.msimbiga.moviesdb.presentation.nowplaying.NowPlayingScreenRoot

@Composable
fun NavigationGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NowPlayingDestination
    ) {
        composable<NowPlayingDestination> {
            NowPlayingScreenRoot(
                onNavigateToDetail = { id ->
                    navController.navigate(DetailScreenDestination(id))
                }
            )
        }

        composable<DetailScreenDestination> {
            DetailScreenRoot(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}