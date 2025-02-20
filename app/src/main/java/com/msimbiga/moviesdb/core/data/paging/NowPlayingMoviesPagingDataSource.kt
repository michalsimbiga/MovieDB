package com.msimbiga.moviesdb.core.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.msimbiga.moviesdb.core.data.datasource.MoviesNetworkDataSource
import com.msimbiga.moviesdb.core.data.models.MovieDTO
import com.msimbiga.moviesdb.core.domain.Result
import com.msimbiga.moviesdb.core.domain.models.Movie
import kotlinx.io.IOException

class NowPlayingMoviesPagingDataSource(
    private val moviesNetworkDataSource: MoviesNetworkDataSource
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val pageIndex = params.key ?: 1
        return try {
            val response = moviesNetworkDataSource.getNowPlaying(page = pageIndex)

            when (response) {
                is Result.Error -> LoadResult.Error(response.error as Throwable)
                is Result.Success -> {
                    val nextKey = if (response.data.page == response.data.totalPages) {
                        null
                    } else {
                        response.data.page.inc()
                    }
                    LoadResult.Page(
                        data = response.data.results.map(MovieDTO::toDomain),
                        prevKey = if (pageIndex == 1) null else pageIndex,
                        nextKey = nextKey
                    )
                }
            }
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        }
    }
}