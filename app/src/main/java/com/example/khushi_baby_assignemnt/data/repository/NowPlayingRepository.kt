package com.example.khushi_baby_assignemnt.ui.fragments.nowplaying.viewmodel

import com.example.khushi_baby_assignemnt.BuildConfig
import com.example.khushi_baby_assignemnt.data.api.RetrofitHelper
import com.example.khushi_baby_assignemnt.data.model.NowPlayingMoviesResponse
import com.example.khushi_baby_assignemnt.utils.Constants
import retrofit2.Response

class NowPlayingRepository {
    suspend fun getNowPlayingMovies(page:Int): Response<NowPlayingMoviesResponse> {
        return RetrofitHelper.responseApiInterface.getMovieNowPlaying(
            "Bearer ${BuildConfig.ACCESS_TOKEN_AUTH}",
            page = page
        )
    }
}
