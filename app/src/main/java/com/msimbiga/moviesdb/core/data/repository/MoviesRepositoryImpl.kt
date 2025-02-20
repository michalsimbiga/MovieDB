package com.msimbiga.moviesdb.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.msimbiga.moviesdb.core.data.datasource.MoviesLocalDataSource
import com.msimbiga.moviesdb.core.data.datasource.MoviesNetworkDataSource
import com.msimbiga.moviesdb.core.data.models.MovieDetailsDTO
import com.msimbiga.moviesdb.core.data.models.NowPlayingPageDTO
import com.msimbiga.moviesdb.core.data.models.SearchPageDTO
import com.msimbiga.moviesdb.core.data.paging.NowPlayingMoviesPagingDataSource
import com.msimbiga.moviesdb.core.domain.Error
import com.msimbiga.moviesdb.core.domain.Result
import com.msimbiga.moviesdb.core.domain.map
import com.msimbiga.moviesdb.core.domain.models.Movie
import com.msimbiga.moviesdb.core.domain.models.MovieDetails
import com.msimbiga.moviesdb.core.domain.models.NowPlayingPage
import com.msimbiga.moviesdb.core.domain.models.SearchPage
import com.msimbiga.moviesdb.core.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class MoviesRepositoryImpl @Inject constructor(
    private val moviesNetworkDataSource: MoviesNetworkDataSource,
    private val moviesLocalDataSource: MoviesLocalDataSource
) : MoviesRepository {

    override suspend fun getNowPlayingPage(page: Int): Result<NowPlayingPage, Error> =
        moviesNetworkDataSource
            .getNowPlaying(page)
            .map(NowPlayingPageDTO::toDomain)

    override suspend fun getSearchPage(
        searchTerm: String,
        page: Int
    ): Result<SearchPage, Error> =
        moviesNetworkDataSource
            .getSearchSuggestions(searchTerm, page)
            .map(SearchPageDTO::toDomain)

    override suspend fun getMovieDetails(id: Int): Result<MovieDetails, Error> =
        moviesNetworkDataSource
            .getMovieDetails(id)
            .map(MovieDetailsDTO::toDomain)

    override suspend fun setMovieLiked(id: Int, isLiked: Boolean): Result<Any, Error> {
        if (isLiked) {
            moviesLocalDataSource.saveMovieId(id)
        } else {
            moviesLocalDataSource.removeMovieId(id)
        }
        return Result.Success(Unit)
    }

    override fun getLikedMoviesFlow(): Flow<List<Int>> =
        moviesLocalDataSource.getLikedMoviesFlow()

    override suspend fun getNowPlayingPagingData(): Flow<PagingData<Movie>> =
        Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 1),
            initialKey = 1,
            pagingSourceFactory = {
                NowPlayingMoviesPagingDataSource(moviesNetworkDataSource)
            }
        ).flow
}