package com.example.khushi_baby_assignemnt.ui.fragments.popular.viewmodel

import android.content.Context
import com.example.khushi_baby_assignemnt.BuildConfig
import com.example.khushi_baby_assignemnt.data.api.RetrofitHelper
import com.example.khushi_baby_assignemnt.data.database.AppDatabase
import com.example.khushi_baby_assignemnt.data.database.dao.MovieDao
import com.example.khushi_baby_assignemnt.data.database.entities.MovieEntity
import com.example.khushi_baby_assignemnt.data.model.PopularMoviesResponse
import com.example.khushi_baby_assignemnt.utils.Constants
import com.example.khushi_baby_assignemnt.utils.NetworkUtils
import retrofit2.Response

class PopularRepository (val context: Context){

    lateinit var  movieDao: MovieDao
//    private val movieDao: MovieDao = AppDatabase.invoke(context).movieDao()


    suspend fun getPopularMovies(page:Int): Response<PopularMoviesResponse> {
        if (NetworkUtils.isNetworkAvailable(context)) {
            return RetrofitHelper.responseApiInterface.getPopularMovies(
                "Bearer ${BuildConfig.ACCESS_TOKEN_AUTH}",
                page = page
            )
        } else {
            return Response.success(PopularMoviesResponse(0, emptyList(), 0, 0))
        }
    }

    suspend fun saveMoviesToDatabase(movies: List<MovieEntity>) {
        movieDao.insertAll(movies)
    }

    suspend fun getSavedMoviesFromDatabase(): List<MovieEntity> {
        return movieDao.getAllMovies()
    }

    suspend fun clearDatabase() {
        movieDao.deleteAll()
    }
}
