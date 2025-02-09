package com.msimbiga.moviesdb.presentation

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.ui.Alignment
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

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                currentDestination?.hierarchy
                    ?.any { it.route in bottomDestinations.map { dest -> dest.route::class.qualifiedName } } == true,
                enter = expandVertically(expandFrom = Alignment.Bottom),
                exit = shrinkVertically(shrinkTowards = Alignment.Bottom)
            ) {
                NavigationBar {
                    bottomDestinations.forEach { screen ->
                        val isSelected =
                            currentDestination?.hierarchy?.any { it.route == screen.route::class.qualifiedName } == true

                        NavigationBarItem(
                            selected = isSelected,
                            onClick = {
                                navController.navigate(screen.route) {
                                    launchSingleTop = true
                                    popUpTo(NowPlayingDestination)
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = screen.selectedIcon,
                                    contentDescription = screen.route.toString()
                                )
                            },
                            label = { Text(screen.title) }
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
