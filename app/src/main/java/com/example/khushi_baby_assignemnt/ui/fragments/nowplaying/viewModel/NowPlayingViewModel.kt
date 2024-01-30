package com.example.khushi_baby_assignemnt.ui.fragments.nowplaying.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.khushi_baby_assignemnt.data.model.MovieDisplayResponse
import com.example.khushi_baby_assignemnt.ui.fragments.nowplaying.paging.NowPlayingPagingSource
import com.example.khushi_baby_assignemnt.data.repository.NowPlayingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NowPlayingViewModel @Inject constructor(
    private val repository: NowPlayingRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _nowPlayingMovies = MutableStateFlow<PagingData<MovieDisplayResponse>?>(null)
    val nowPlayingMovies: Flow<PagingData<MovieDisplayResponse>?> = _nowPlayingMovies

    private val _error = MutableStateFlow<String?>(null)
    val error: Flow<String?> = _error

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
                _error.value = "Error occurred: ${e.message}"
            }
        }
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }
}
