package com.example.khushi_baby_assignemnt.ui.fragments.popular.paging
import android.content.Context
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.khushi_baby_assignemnt.data.database.entities.MovieEntity
import com.example.khushi_baby_assignemnt.data.model.MovieResponse
import com.example.khushi_baby_assignemnt.data.repository.PopularRepository
import com.example.khushi_baby_assignemnt.utils.NetworkUtils
import java.text.SimpleDateFormat

class PopularPagingSource(private val repository: PopularRepository, val context: Context) :
    PagingSource<Int, MovieResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResponse> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = if (NetworkUtils.isNetworkAvailable(context)) {
                repository.getPopularMovies(page)
            } else {
                val localMovies = repository.getSavedMoviesFromDatabase().map { movieEntity ->
                    MovieResponse(
                        id = movieEntity.id,
                        title = movieEntity.title,
                        overview = movieEntity.overview,
                        poster_path = movieEntity.posterPath,
                        release_date = SimpleDateFormat("yyyy-MM-dd").parse(movieEntity.releaseDate),
                        vote_average = movieEntity.voteAverage,
                        vote_count = movieEntity.voteCount.toDouble(),
                        backdrop_path = "",
                        original_language = "",
                        popularity =  00.00,
                        original_title = ""
                    )
                }
                return LoadResult.Page(
                    data = localMovies,
                    prevKey = null,
                    nextKey = null
                )
            }
            val movieResponses = response.body()?.results ?: emptyList()
            val movieEntities = movieResponses.map { movieResponse ->
                MovieEntity(
                    id = movieResponse.id,
                    title = movieResponse.title,
                    overview = movieResponse.overview,
                    posterPath = movieResponse.poster_path,
                    releaseDate = SimpleDateFormat("yyyy-MM-dd").format(movieResponse.release_date),
                    voteAverage = movieResponse.vote_average,
                    voteCount = movieResponse.vote_count.toInt()
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

    override fun getRefreshKey(state: PagingState<Int, MovieResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }
}
