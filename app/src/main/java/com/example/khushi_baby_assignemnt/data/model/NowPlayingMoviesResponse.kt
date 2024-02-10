package com.example.khushi_baby_assignemnt.data.model
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class NowPlayingMoviesResponse(
    @SerializedName("dates") val dates: Dates,
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: MutableList<MovieDisplayResponse>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)
