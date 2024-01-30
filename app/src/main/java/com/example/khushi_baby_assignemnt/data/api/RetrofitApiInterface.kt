package com.example.khushi_baby_assignemnt.data.api

import com.example.khushi_baby_assignemnt.data.model.MovieDetailsResponse
import com.example.khushi_baby_assignemnt.data.model.MovieDisplayResponse
import com.example.khushi_baby_assignemnt.data.model.MovieResponse
import com.example.khushi_baby_assignemnt.data.model.NowPlayingMoviesResponse
import com.example.khushi_baby_assignemnt.data.model.PopularMoviesResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitApiInterface {
    @GET("movie/now_playing")
    suspend fun getMovieNowPlaying(
        @Header("Authorization") authorization: String,
        @Query("page") page : Int
    ): Response<NowPlayingMoviesResponse>


    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Header("Authorization") authorization: String,
        @Query("page") page : Int
    ): Response<PopularMoviesResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Header("Authorization") authorization: String
    ): Response<MovieDetailsResponse>

}