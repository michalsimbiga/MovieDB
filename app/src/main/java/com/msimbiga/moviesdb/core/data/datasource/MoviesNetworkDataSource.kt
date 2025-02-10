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

    suspend fun getNowPlaying(page: Int): Result<NowPlayingPageDTO, DataError.Network> {
        Log.d("Temporial", "Fetch now playing $page")
        val response = safeCall<NowPlayingPageDTO> {
            httpClient.get("https://api.themoviedb.org/3/movie/now_playing") {
                url {
                    headers.append("Authorization", "Bearer $apiKey")
                    parameters.append("page", page.toString())
                }
            }
        }

        Log.d("VUKO", "Movies are $response")

        return response
    }

    suspend fun getMovieDetails(id: Int): Result<MovieDetailsDTO, DataError.Network> {
        val response = safeCall<MovieDetailsDTO> {
            httpClient.get("https://api.themoviedb.org/3/movie/$id") {
                url { headers.append("Authorization", "Bearer $apiKey") }
            }
        }

        Log.d("VUKO", "Movie details are $response")

        return response
    }

    suspend fun getSearchSuggestions(
        searchTerm: String,
        page: Int
    ): Result<SearchPageDTO, DataError.Network> {
        val response = safeCall<SearchPageDTO> {
            httpClient.get("https://api.themoviedb.org/3/search/movie") {
                url {
                    headers.append("Authorization", "Bearer $apiKey")
                    parameters.append("query", searchTerm)
                    parameters.append("page", page.toString())

                }
            }
        }

        Log.d("VUKO", "Movie suggestions are $response")

        return response
    }
}