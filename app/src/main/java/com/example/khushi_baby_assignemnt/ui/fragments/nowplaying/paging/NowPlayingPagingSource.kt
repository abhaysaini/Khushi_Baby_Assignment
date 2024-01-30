package com.example.khushi_baby_assignemnt.ui.fragments.nowplaying.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.khushi_baby_assignemnt.data.model.MovieDisplayResponse
import com.example.khushi_baby_assignemnt.data.repository.NowPlayingRepository

class NowPlayingPagingSource(private val repository: NowPlayingRepository) :
    PagingSource<Int, MovieDisplayResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDisplayResponse> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = repository.getNowPlayingMovies(page)
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