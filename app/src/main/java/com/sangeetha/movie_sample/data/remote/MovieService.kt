package com.sangeetha.movie_sample.data.remote

import com.sangeetha.movie_sample.core.Constants.API_KEY
import com.sangeetha.movie_sample.core.Constants.STARTING_PAGE_INDEX
import com.sangeetha.movie_sample.data.NowPlayingResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int = STARTING_PAGE_INDEX,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en"
    ): NowPlayingResponse
}