package com.example.khushi_baby_assignemnt.ui.fragments.popular.viewmodel

import com.example.khushi_baby_assignemnt.BuildConfig
import com.example.khushi_baby_assignemnt.data.api.RetrofitHelper
import com.example.khushi_baby_assignemnt.data.model.PopularMoviesResponse
import com.example.khushi_baby_assignemnt.utils.Constants
import retrofit2.Response

class PopularRepository {

    suspend fun getPopularMovies(page:Int): Response<PopularMoviesResponse> {
        return RetrofitHelper.responseApiInterface.getPopularMovies(
            "Bearer ${BuildConfig.ACCESS_TOKEN_AUTH}",
            page = page
        )
    }
}
