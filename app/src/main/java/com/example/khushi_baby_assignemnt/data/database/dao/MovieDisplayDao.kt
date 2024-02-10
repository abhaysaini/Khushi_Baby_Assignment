package com.example.khushi_baby_assignemnt.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.khushi_baby_assignemnt.data.database.entities.MovieDisplayEntity

@Dao
interface MovieDisplayDao {

    @Query("SELECT * FROM movie_display")
    suspend fun getAllNowPlayingMovies(): List<MovieDisplayEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllNowPlaying(movies: List<MovieDisplayEntity>)

    @Query("DELETE FROM movie_display")
    suspend fun deleteAllNowPlaying()
}