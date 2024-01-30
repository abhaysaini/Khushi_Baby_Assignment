package com.example.khushi_baby_assignemnt.data.repository

import com.example.khushi_baby_assignemnt.BuildConfig
import com.example.khushi_baby_assignemnt.data.api.RetrofitApiInterface
import com.example.khushi_baby_assignemnt.data.model.NowPlayingMoviesResponse
import com.example.khushi_baby_assignemnt.utils.Constants
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NowPlayingRepository  @Inject constructor(val retrofitApiInterface: RetrofitApiInterface){
    suspend fun getNowPlayingMovies(page: Int): Response<NowPlayingMoviesResponse> {
        return retrofitApiInterface.getMovieNowPlaying(
            "Bearer ${BuildConfig.ACCESS_TOKEN_AUTH}",
            page = page
        )
    }
}
