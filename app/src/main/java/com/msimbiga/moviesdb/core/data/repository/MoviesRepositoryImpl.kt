package com.msimbiga.moviesdb.core.data.repository

import com.msimbiga.moviesdb.core.data.models.NowPlayingPageDTO
import com.msimbiga.moviesdb.core.data.service.MoviesNetworkDataSource
import com.msimbiga.moviesdb.core.domain.Error
import com.msimbiga.moviesdb.core.domain.Result
import com.msimbiga.moviesdb.core.domain.map
import com.msimbiga.moviesdb.core.domain.models.Movie
import com.msimbiga.moviesdb.core.domain.models.NowPlayingPage
import com.msimbiga.moviesdb.core.domain.repository.MoviesRepository
import javax.inject.Inject


class MoviesRepositoryImpl @Inject constructor(
    private val moviesNetworkDataSource: MoviesNetworkDataSource
) : MoviesRepository {

    override suspend fun fetchNowPlayingPage(page: Int?): Result<NowPlayingPage, Error> =
        moviesNetworkDataSource
            .getNowPlaying()
            .map(NowPlayingPageDTO::toDomain)

    override suspend fun getMovieDetails(id: Int): Result<Movie, Error> {
        TODO("Not yet implemented")
    }
}