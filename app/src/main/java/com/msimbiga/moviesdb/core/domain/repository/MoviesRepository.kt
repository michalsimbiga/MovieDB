package com.msimbiga.moviesdb.core.domain.repository

import com.msimbiga.moviesdb.core.domain.Error
import com.msimbiga.moviesdb.core.domain.Result
import com.msimbiga.moviesdb.core.domain.models.MovieDetails
import com.msimbiga.moviesdb.core.domain.models.NowPlayingPage

interface MoviesRepository {

    suspend fun fetchNowPlayingPage(page: Int?): Result<NowPlayingPage, Error>

    suspend fun getMovieDetails(id: Int): Result<MovieDetails, Error>

}