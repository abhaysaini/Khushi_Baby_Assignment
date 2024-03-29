package com.example.khushi_baby_assignemnt.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.khushi_baby_assignemnt.data.database.dao.MovieDao
import com.example.khushi_baby_assignemnt.data.database.dao.MovieDisplayDao
import com.example.khushi_baby_assignemnt.data.database.entities.MovieDisplayEntity
import com.example.khushi_baby_assignemnt.data.database.entities.MovieEntity


@Database(entities = [MovieEntity::class, MovieDisplayEntity::class], version = 1)

abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun movieDisplayDao(): MovieDisplayDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context): AppDatabase {
            return instance ?: synchronized(LOCK) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "movie_database"
            ).build()
    }
}
