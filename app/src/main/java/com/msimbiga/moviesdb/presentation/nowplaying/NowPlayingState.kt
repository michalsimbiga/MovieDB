package com.msimbiga.moviesdb.presentation.nowplaying

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import com.msimbiga.moviesdb.presentation.models.MovieItem

@Immutable
sealed interface NowPlayingState {
    data object Loading : NowPlayingState
    data object Error : NowPlayingState
    data class Success(
        val moviesPagingData: PagingData<MovieItem> = PagingData.empty()
    ) : NowPlayingState
}

