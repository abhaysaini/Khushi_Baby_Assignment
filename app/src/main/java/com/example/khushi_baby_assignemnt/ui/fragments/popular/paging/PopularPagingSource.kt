package com.example.khushi_baby_assignemnt.ui.fragments.popular.paging
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.khushi_baby_assignemnt.data.model.MovieResponse
import com.example.khushi_baby_assignemnt.data.model.NowPlayingMoviesResponse
import com.example.khushi_baby_assignemnt.ui.fragments.popular.viewmodel.PopularRepository

class PopularPagingSource(private val repository: PopularRepository) : PagingSource<Int, MovieResponse>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResponse> {
        try {
            Log.i("paging", "Loading popular movies from PagingSource")
            val nextPageNumber = params.key ?: 1
            val response = repository.getPopularMovies(nextPageNumber)
            return if (response.isSuccessful) {
                val mov = mutableListOf<MovieResponse>()
                val data = response.body()?.results ?: emptyList()
                mov.addAll(data)
                Log.i("paging",data.toString())
                LoadResult.Page(
                    data = mov,
                    prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1,
                    nextKey = if (mov.isEmpty()) null else nextPageNumber + 1
                )
            } else {
                LoadResult.Error(Exception("Failed to fetch popular movies: ${response.message()}"))
            }
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieResponse>): Int? {
        return null
    }
}
