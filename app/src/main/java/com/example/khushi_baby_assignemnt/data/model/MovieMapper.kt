package com.example.khushi_baby_assignemnt.data.model
import android.util.Log
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*

object MovieMapper {

    fun mapToMovieDisplayResponse(response: String): List<MovieDisplayResponse> {
        val gson = Gson()
        val responseData = gson.fromJson(response, NowPlayingMoviesResponse::class.java)
        Log.d("abhay", responseData.toString())
        val movieList = mutableListOf<MovieDisplayResponse>()
        responseData.results.forEach { movie ->
            val movieDisplayResponse = MovieDisplayResponse(
                id = movie.id,
                original_title = movie.original_title,
                overview = movie.overview,
                popularity = movie.popularity,
                poster_path = movie.poster_path,
                release_date = parseDateString(movie.release_date),
                title = movie.title,
                vote_average = movie.vote_average,
                vote_count = movie.vote_count
            )
            movieList.add(movieDisplayResponse)
        }
        return movieList
    }

    fun mapToMovieResponse(response: String): List<MovieResponse> {
        val gson = Gson()
        val responseData = gson.fromJson(response, PopularMoviesResponse::class.java)
        return responseData.results.map { movie ->
            MovieResponse(
                id = movie.id,
                backdrop_path = movie.backdrop_path,
                original_language = movie.original_language,
                original_title = movie.original_title,
                overview = movie.overview,
                popularity = movie.popularity,
                poster_path = movie.poster_path,
                release_date = parseDateString(movie.release_date),
                title = movie.title,
                vote_average = movie.vote_average,
                vote_count = movie.vote_count
            )
        }
    }

    private fun parseDateString(dateString: Date): Date {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return formatter.parse(dateString.toString()) ?: Date()
    }
}
