package com.example.khushi_baby_assignemnt.data.repository

import android.content.Context
import com.example.khushi_baby_assignemnt.BuildConfig
import com.example.khushi_baby_assignemnt.data.api.RetrofitHelper
import com.example.khushi_baby_assignemnt.data.database.AppDatabase
import com.example.khushi_baby_assignemnt.data.database.dao.MovieDao
import com.example.khushi_baby_assignemnt.data.database.entities.MovieEntity
import com.example.khushi_baby_assignemnt.data.model.NowPlayingMoviesResponse
import com.example.khushi_baby_assignemnt.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class NowPlayingRepository(private val context: Context) {

//    private val movieDao: MovieDao = AppDatabase.invoke(context).movieDao()

    suspend fun getNowPlayingMovies(page:Int): Response<NowPlayingMoviesResponse> {
        return RetrofitHelper.responseApiInterface.getMovieNowPlaying(
            "Bearer ${BuildConfig.ACCESS_TOKEN_AUTH}",
            page = page
        )
    }

//    suspend fun saveMoviesToDatabase(movies: List<MovieEntity>) {
//        withContext(Dispatchers.IO) {
//            movieDao.insertAll(movies)
//        }
//    }
//
//    suspend fun getSavedMoviesFromDatabase(): List<MovieEntity> {
//        return withContext(Dispatchers.IO) {
//            movieDao.getAllMovies()
//        }
//    }
}
