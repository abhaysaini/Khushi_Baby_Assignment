package com.example.khushi_baby_assignemnt.data.repository

import com.example.khushi_baby_assignemnt.BuildConfig
import com.example.khushi_baby_assignemnt.data.api.RetrofitApiInterface
import com.example.khushi_baby_assignemnt.data.model.MovieDetailsResponse
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieDetailRepository @Inject constructor(var retrofitApiInterface: RetrofitApiInterface ){
    suspend fun getMovieDetails(movieId: Int): Response<MovieDetailsResponse> {
        return retrofitApiInterface.getMovieDetails(
            movieId,
            "Bearer ${BuildConfig.ACCESS_TOKEN_AUTH}"
        )
    }
}
