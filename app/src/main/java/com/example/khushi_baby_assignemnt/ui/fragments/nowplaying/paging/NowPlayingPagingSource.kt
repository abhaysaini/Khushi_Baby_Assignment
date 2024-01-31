package com.example.khushi_baby_assignemnt.ui.fragments.nowplaying.paging

import android.content.Context
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.khushi_baby_assignemnt.data.database.entities.MovieDisplayEntity
import com.example.khushi_baby_assignemnt.data.model.MovieDisplayResponse
import com.example.khushi_baby_assignemnt.data.repository.NowPlayingRepository
import com.example.khushi_baby_assignemnt.utils.NetworkUtils
import java.text.SimpleDateFormat

class NowPlayingPagingSource(private val repository: NowPlayingRepository, val context: Context) :
    PagingSource<Int, MovieDisplayResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDisplayResponse> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = if (NetworkUtils.isNetworkAvailable(context)) {
                repository.getNowPlayingMovies(page)
            } else {
                val localMovies = repository.getSavedMoviesFromDatabase().map { movieEntity ->
                    MovieDisplayResponse(
                        id = movieEntity.id,
                        original_title = movieEntity.original_title,
                        overview = movieEntity.overview,
                        popularity = movieEntity.popularity,
                        poster_path = movieEntity.poster_path,
                        release_date = SimpleDateFormat("yyyy-MM-dd").parse(movieEntity.release_date),
                        title = movieEntity.title,
                        vote_average = movieEntity.vote_average,
                        vote_count = movieEntity.vote_count
                    )
                }
                return LoadResult.Page(
                    data = localMovies,
                    prevKey = null,
                    nextKey = null
                )
            }
            val movieResponses = response.body()?.results ?: emptyList()
            val movieEntities = movieResponses.map { movieDisplayResponse ->
                MovieDisplayEntity(
                    id = movieDisplayResponse.id,
                    original_title = movieDisplayResponse.original_title,
                    overview = movieDisplayResponse.overview,
                    popularity = movieDisplayResponse.popularity,
                    poster_path = movieDisplayResponse.poster_path,
                    release_date = SimpleDateFormat("yyyy-MM-dd").format(movieDisplayResponse.release_date),
                    title = movieDisplayResponse.title,
                    vote_average = movieDisplayResponse.vote_average,
                    vote_count = movieDisplayResponse.vote_count
                )
            }
            repository.saveMoviesToDatabase(movieEntities)
            LoadResult.Page(
                data = response.body()?.results ?: emptyList(),
                prevKey = if (page == STARTING_PAGE_INDEX) null else page.minus(1),
                nextKey = if (response.body()?.results!!.isEmpty()) null else page.plus(1)
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieDisplayResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }
}


//
//import androidx.paging.PagingSource
//import androidx.paging.PagingState
//import com.example.khushi_baby_assignemnt.data.database.entities.MovieDisplayEntity
//import com.example.khushi_baby_assignemnt.data.model.MovieDisplayResponse
//import com.example.khushi_baby_assignemnt.data.repository.NowPlayingRepository
//import java.text.SimpleDateFormat
//import java.util.Calendar
//import java.util.Date
//import java.util.Locale
//
//class NowPlayingPagingSource(private val repository: NowPlayingRepository) :
//    PagingSource<Int, MovieDisplayResponse>() {
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDisplayResponse> {
//        val page = params.key ?: STARTING_PAGE_INDEX
//        return try {
//            val response = repository.getNowPlayingMovies(page)
//            val movieResponses = response.body()?.results ?: emptyList()
//            val movieEntities = movieResponses.map { movieDisplayResponse ->
//                MovieDisplayEntity(
//                    id = movieDisplayResponse.id,
//                    original_title = movieDisplayResponse.original_title,
//                    overview = movieDisplayResponse.overview,
//                    popularity = movieDisplayResponse.popularity,
//                    poster_path = movieDisplayResponse.poster_path,
//                    release_date = SimpleDateFormat("yyyy-MM-dd").format(movieDisplayResponse.release_date),
//                    title = movieDisplayResponse.title,
//                    vote_average = movieDisplayResponse.vote_average,
//                    vote_count = movieDisplayResponse.vote_count
//                )
//            }
//            repository.saveMoviesToDatabase(movieEntities)
//            LoadResult.Page(
//                data = response.body()?.results ?: emptyList(),
//                prevKey = if (page == STARTING_PAGE_INDEX) null else page.minus(1),
//                nextKey = if (response.body()?.results!!.isEmpty()) null else page.plus(1)
//            )
//        } catch (exception: Exception) {
//            return LoadResult.Error(exception)
//        }
//    }
//    override fun getRefreshKey(state: PagingState<Int, MovieDisplayResponse>): Int? {
//        return state.anchorPosition?.let { anchorPosition ->
//            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
//                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
//        }
//    }
//
//    companion object {
//        private const val STARTING_PAGE_INDEX = 1
//    }
//}