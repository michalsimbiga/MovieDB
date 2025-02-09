package com.msimbiga.moviesdb.presentation

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.msimbiga.moviesdb.presentation.detail.DetailScreenDestination
import com.msimbiga.moviesdb.presentation.detail.DetailScreenRoot
import com.msimbiga.moviesdb.presentation.favourites.FavouritesDestination
import com.msimbiga.moviesdb.presentation.favourites.FavouritesScreenRoot
import com.msimbiga.moviesdb.presentation.nowplaying.NowPlayingDestination
import com.msimbiga.moviesdb.presentation.nowplaying.NowPlayingScreenRoot
import com.msimbiga.moviesdb.presentation.search.SearchScreenDestination
import com.msimbiga.moviesdb.presentation.search.SearchScreenRoot
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
sealed class BottomScreen<T>(
    val title: String,
    val route: T,
    @Contextual val selectedIcon: ImageVector,
    @Contextual val unselectedIcon: ImageVector,
) {
    @Serializable
    data object NowPlaying : BottomScreen<NowPlayingDestination>(
        "Now Playing",
        NowPlayingDestination,
        selectedIcon = Icons.Default.PlayArrow,
        unselectedIcon = Icons.Outlined.PlayArrow
    )

    @Serializable
    data object Search : BottomScreen<SearchScreenDestination>(
        "Search",
        SearchScreenDestination,
        selectedIcon = Icons.Default.Search,
        unselectedIcon = Icons.Outlined.Search
    )

    @Serializable
    data object Favourites : BottomScreen<FavouritesDestination>(
        "Favourites",
        FavouritesDestination,
        selectedIcon = Icons.Default.Favorite,
        unselectedIcon = Icons.Outlined.FavoriteBorder
    )
}

@SuppressLint("RestrictedApi")
@Composable
fun NavigationGraph() {
    val bottomDestinations = remember {
        listOf(
            BottomScreen.NowPlaying,
            BottomScreen.Search,
            BottomScreen.Favourites
        )
    }

    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination


    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                true
//                bottomDestinations.map { it.toString() }
//                    .contains(currentDestination.toString())
//                currentDestination.value?.destination?.route in topLevelDestinations.map { it.route }
            ) {
                NavigationBar {
                    bottomDestinations.forEachIndexed { index, item ->
                        val isSelected = currentDestination
                            ?.hierarchy
                            ?.any {
                                it.hasRoute(
                                    route = item.toString(),
                                    arguments = null
                                )
                            } == true
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = {
                                navController.navigate(item.route) {
                                    launchSingleTop = true
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = item.selectedIcon,
                                    contentDescription = item.route.toString()
                                )
                            },
                            label = { Text(item.title) }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->

        NavHost(
            modifier = Modifier.padding(paddingValues = paddingValues),
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

            composable<SearchScreenDestination> {
                SearchScreenRoot(
                    onNavigateToDetails = { id ->
                        navController.navigate(DetailScreenDestination(id))
                    }
                )
            }

            composable<FavouritesDestination> {
                FavouritesScreenRoot(
                    onNavigateToDetails = { id ->
                        navController.navigate(DetailScreenDestination(id))
                    },
                    onNavigateBackClicked = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
