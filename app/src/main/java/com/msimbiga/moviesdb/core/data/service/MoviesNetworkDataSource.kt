package com.msimbiga.moviesdb.core.data.service

import android.util.Log
import com.msimbiga.moviesdb.core.data.extensions.safeCall
import com.msimbiga.moviesdb.core.data.models.NowPlayingPageDTO
import com.msimbiga.moviesdb.core.domain.DataError
import com.msimbiga.moviesdb.core.domain.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import javax.inject.Inject

class MoviesNetworkDataSource @Inject constructor(
    private val httpClient: HttpClient
) {

    suspend fun getNowPlaying(): Result<NowPlayingPageDTO, DataError.Network> {
        val response = safeCall<NowPlayingPageDTO> {
            httpClient.get("https://api.themoviedb.org/3/movie/now_playing") {
                url {
                    headers.append("Authorization", "Bearer $API_ACCESS_TOKEN")
                }
            }
        }

        Log.d("VUKO", "Movies are $response")

        return response
    }
}