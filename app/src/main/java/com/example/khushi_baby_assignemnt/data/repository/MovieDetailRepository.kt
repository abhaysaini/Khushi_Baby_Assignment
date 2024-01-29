package com.example.khushi_baby_assignemnt.data.repository

import com.example.khushi_baby_assignemnt.BuildConfig
import com.example.khushi_baby_assignemnt.data.api.RetrofitHelper
import com.example.khushi_baby_assignemnt.data.model.MovieDetailsResponse
import retrofit2.Response

class MovieDetailRepository {
    suspend fun getMovieDetails(movieId: Int): Response<MovieDetailsResponse> {
        return RetrofitHelper.responseApiInterface.getMovieDetails(
            movieId,
            "Bearer ${BuildConfig.ACCESS_TOKEN_AUTH}"
        )
    }
}
