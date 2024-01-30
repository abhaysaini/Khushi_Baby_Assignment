package com.example.khushi_baby_assignemnt.data.repository

import com.example.khushi_baby_assignemnt.BuildConfig
import com.example.khushi_baby_assignemnt.data.api.RetrofitApiInterface
import com.example.khushi_baby_assignemnt.data.model.PopularMoviesResponse
import com.example.khushi_baby_assignemnt.utils.Constants
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PopularRepository  @Inject constructor(var retrofitApiInterface: RetrofitApiInterface){
    suspend fun getPopularMovies(page:Int): Response<PopularMoviesResponse> {
        return retrofitApiInterface.getPopularMovies(
            "Bearer ${BuildConfig.ACCESS_TOKEN_AUTH}",
            page = page
        )
    }
}
