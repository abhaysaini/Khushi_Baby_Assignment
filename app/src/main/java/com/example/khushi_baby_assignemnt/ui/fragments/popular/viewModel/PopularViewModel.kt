// PopularViewModel.kt
package com.example.khushi_baby_assignemnt.ui.fragments.popular.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.khushi_baby_assignemnt.data.model.MovieResponse
import com.example.khushi_baby_assignemnt.ui.fragments.popular.paging.PopularPagingSource
import com.example.khushi_baby_assignemnt.data.repository.PopularRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PopularViewModel(private val repository: PopularRepository, val context: Context) : ViewModel() {

    private val _popularMovies = MutableStateFlow<PagingData<MovieResponse>?>(null)
    val popularMovies: Flow<PagingData<MovieResponse>?> = _popularMovies

    private val _error = MutableLiveData<String?>(null)
    val error: MutableLiveData<String?> = _error

    init {
        fetchPopularMovies()
    }

    fun fetchPopularMovies() {
        viewModelScope.launch {
            try {
                val pagingSource = PopularPagingSource(repository, context)
                val pager = Pager(
                    config = PagingConfig(
                        pageSize = NETWORK_PAGE_SIZE
                    ),
                    pagingSourceFactory = { pagingSource }
                ).flow

                pager.cachedIn(viewModelScope).collectLatest { pagingData ->
                    _popularMovies.value = pagingData
                }
            } catch (e: Exception) {
                _error.postValue("Error occurred: ${e.message}")
            }
        }
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }
}
