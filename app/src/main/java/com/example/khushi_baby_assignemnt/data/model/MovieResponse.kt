package com.example.khushi_baby_assignemnt.data.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class MovieResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("backdrop_path")
    val backdrop_path: String,

    @SerializedName("original_language")
    val original_language: String,

    @SerializedName("original_title")
    val original_title: String,

    @SerializedName("overview")
    val overview: String,

    @SerializedName("popularity")
    val popularity: Double,

    @SerializedName("poster_path")
    val poster_path: String,

    @SerializedName("release_date")
    val release_date: Date,

    @SerializedName("title")
    val title: String,

    @SerializedName("vote_average")
    val vote_average: Double,

    @SerializedName("vote_count")
    val vote_count: Double
)