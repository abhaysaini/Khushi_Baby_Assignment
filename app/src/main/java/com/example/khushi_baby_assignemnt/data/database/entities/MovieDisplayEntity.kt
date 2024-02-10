package com.example.khushi_baby_assignemnt.data.database.entities
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "movie_display")
data class MovieDisplayEntity(
    @PrimaryKey val id: Int,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val vote_average: Double,
    val vote_count: Double
)
