package com.msimbiga.moviesdb.core.data.datasource

import android.util.Log
import com.msimbiga.moviesdb.core.data.extensions.safeCall
import com.msimbiga.moviesdb.core.data.models.MovieDetailsDTO
import com.msimbiga.moviesdb.core.data.models.NowPlayingPageDTO
import com.msimbiga.moviesdb.core.data.models.SearchPageDTO
import com.msimbiga.moviesdb.core.domain.DataError
import com.msimbiga.moviesdb.core.domain.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import javax.inject.Inject


class MoviesNetworkDataSource @Inject constructor(
    private val httpClient: HttpClient,
    private val apiKey: String,
) {

    private val BASE_URL = "https://api.themoviedb.org/3/"

    suspend fun getNowPlaying(page: Int): Result<NowPlayingPageDTO, DataError.Network> {
        val response = safeCall<NowPlayingPageDTO> {
            httpClient.get("$BASE_URL/movie/now_playing") {
                url {
                    headers.append("Authorization", "Bearer $apiKey")
                    parameters.append("page", page.toString())
                }
            }
        }

        when (response) {
            is Result.Error -> {
                Log.d("MoviesNetworkDataSource", "Now playing response failure ${response}")
            }
            is Result.Success -> {
                Log.d(
                    "MoviesNetworkDataSource", "Now playing response success" +
                            " page:${response.data.page}" +
                            " totalPages:${response.data.totalPages}" +
                            " totalResults:${response.data.totalResults}"
                )
            }
        }
        return response
    }

    suspend fun getMovieDetails(id: Int): Result<MovieDetailsDTO, DataError.Network> {
        val response = safeCall<MovieDetailsDTO> {
            httpClient.get("$BASE_URL/movie/$id") {
                url { headers.append("Authorization", "Bearer $apiKey") }
            }
        }

        Log.d("MoviesNetworkDataSource", "Movie details response $response")
        return response
    }

    suspend fun getSearchSuggestions(
        searchTerm: String,
        page: Int
    ): Result<SearchPageDTO, DataError.Network> {
        val response = safeCall<SearchPageDTO> {
            httpClient.get("$BASE_URL/search/movie") {
                url {
                    headers.append("Authorization", "Bearer $apiKey")
                    parameters.append("query", searchTerm)
                    parameters.append("page", page.toString())

                }
            }
        }

        when (response) {
            is Result.Error -> {
                Log.d("VUKO", "Search response failure ${response}")
            }
            is Result.Success -> {
                Log.d(
                    "MoviesNetworkDataSource", "Search response success" +
                            " page:${response.data.page}" +
                            " totalPages:${response.data.totalPages}" +
                            " totalResults:${response.data.totalResults}"
                )
            }
        }
        return response
    }
}