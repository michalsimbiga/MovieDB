package com.msimbiga.moviesdb.core.data.repository

import com.msimbiga.moviesdb.core.data.datasource.MoviesNetworkDataSource
import com.msimbiga.moviesdb.core.data.models.MovieDetailsDTO
import com.msimbiga.moviesdb.core.data.models.NowPlayingPageDTO
import com.msimbiga.moviesdb.core.data.models.SearchPageDTO
import com.msimbiga.moviesdb.core.domain.Error
import com.msimbiga.moviesdb.core.domain.Result
import com.msimbiga.moviesdb.core.domain.map
import com.msimbiga.moviesdb.core.domain.models.MovieDetails
import com.msimbiga.moviesdb.core.domain.models.NowPlayingPage
import com.msimbiga.moviesdb.core.domain.models.SearchPage
import com.msimbiga.moviesdb.core.domain.repository.MoviesRepository
import javax.inject.Inject


class MoviesRepositoryImpl @Inject constructor(
    private val moviesNetworkDataSource: MoviesNetworkDataSource
) : MoviesRepository {

    override suspend fun fetchNowPlayingPage(page: Int?): Result<NowPlayingPage, Error> =
        moviesNetworkDataSource
            .getNowPlaying()
            .map(NowPlayingPageDTO::toDomain)

    override suspend fun fetchSearchPage(
        searchTerm: String,
        page: Int?
    ): Result<SearchPage, Error> =
        moviesNetworkDataSource
            .getSearchSuggestions(searchTerm)
            .map(SearchPageDTO::toDomain)

    override suspend fun getMovieDetails(id: Int): Result<MovieDetails, Error> =
        moviesNetworkDataSource
            .getMovieDetails(id)
            .map(MovieDetailsDTO::toDomain)

}