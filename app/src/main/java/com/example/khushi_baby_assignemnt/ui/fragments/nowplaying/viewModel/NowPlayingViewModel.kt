// NowPlayingViewModel.kt
package com.example.khushi_baby_assignemnt.ui.fragments.nowplaying.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.khushi_baby_assignemnt.data.model.MovieDisplayResponse
import com.example.khushi_baby_assignemnt.data.model.MovieResponse
import com.example.khushi_baby_assignemnt.ui.fragments.nowplaying.paging.NowPlayingPagingSource
import com.example.khushi_baby_assignemnt.ui.fragments.nowplaying.viewmodel.NowPlayingRepository
import com.example.khushi_baby_assignemnt.ui.fragments.popular.paging.PopularPagingSource
import com.example.khushi_baby_assignemnt.ui.fragments.popular.viewmodel.PopularRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NowPlayingViewModel(private val repository: NowPlayingRepository) : ViewModel() {

    private val _nowPlayingMovies = MutableStateFlow<PagingData<MovieDisplayResponse>?>(null)
    val nowPlayingMovies: Flow<PagingData<MovieDisplayResponse>?> = _nowPlayingMovies

    private val _error = MutableLiveData<String?>(null)
    val error: MutableLiveData<String?> = _error

    init {
        fetchNowPlayingMovies()
    }

    fun fetchNowPlayingMovies() {
        viewModelScope.launch {
            try {

                val pagingSource = NowPlayingPagingSource(repository)
                val pager = Pager(
                    config = PagingConfig(
                        pageSize = NETWORK_PAGE_SIZE
                    ),
                    pagingSourceFactory = { pagingSource }
                ).flow

                pager.cachedIn(viewModelScope).collectLatest { pagingData ->
                    _nowPlayingMovies.value = pagingData
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
