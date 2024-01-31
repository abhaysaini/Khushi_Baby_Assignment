package com.example.khushi_baby_assignemnt.data.repository

import android.content.Context
import com.example.khushi_baby_assignemnt.BuildConfig
import com.example.khushi_baby_assignemnt.data.api.RetrofitHelper
import com.example.khushi_baby_assignemnt.data.database.AppDatabase
import com.example.khushi_baby_assignemnt.data.database.dao.MovieDisplayDao
import com.example.khushi_baby_assignemnt.data.database.entities.MovieDisplayEntity
import com.example.khushi_baby_assignemnt.data.model.NowPlayingMoviesResponse
import retrofit2.Response

class NowPlayingRepository(context: Context) {

    private val movieDisplayDao: MovieDisplayDao = AppDatabase.invoke(context).movieDisplayDao()
    suspend fun getNowPlayingMovies(page:Int): Response<NowPlayingMoviesResponse> {
        return RetrofitHelper.responseApiInterface.getMovieNowPlaying(
            "Bearer ${BuildConfig.ACCESS_TOKEN_AUTH}",
            page = page
        )
    }

    suspend fun saveMoviesToDatabase(movies: List<MovieDisplayEntity>) {
        movieDisplayDao.insertAllNowPlaying(movies)
    }

    suspend fun getSavedMoviesFromDatabase(): List<MovieDisplayEntity> {
        return movieDisplayDao.getAllNowPlayingMovies()
    }

    suspend fun clearDatabase() {
        movieDisplayDao.deleteAllNowPlaying()
    }
}
