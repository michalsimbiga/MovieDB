package com.msimbiga.moviesdb.core.domain.repository

import androidx.paging.PagingData
import com.msimbiga.moviesdb.core.domain.Error
import com.msimbiga.moviesdb.core.domain.Result
import com.msimbiga.moviesdb.core.domain.models.Movie
import com.msimbiga.moviesdb.core.domain.models.MovieDetails
import com.msimbiga.moviesdb.core.domain.models.NowPlayingPage
import com.msimbiga.moviesdb.core.domain.models.SearchPage
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    suspend fun getNowPlayingPage(page: Int): Result<NowPlayingPage, Error>
    suspend fun getSearchPage(searchTerm: String, page: Int): Result<SearchPage, Error>
    suspend fun getMovieDetails(id: Int): Result<MovieDetails, Error>

    suspend fun setMovieLiked(id: Int, isLiked: Boolean): Result<Any, Error>
    fun getLikedMoviesFlow(): Flow<List<Int>>

    suspend fun getNowPlayingPagingData(): Flow<PagingData<Movie>>
    suspend fun getSearchPagingData(searchTerm: String): Flow<PagingData<Movie>>
}