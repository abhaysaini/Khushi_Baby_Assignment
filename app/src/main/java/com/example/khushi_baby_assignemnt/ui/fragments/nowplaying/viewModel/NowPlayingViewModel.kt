// NowPlayingViewModel.kt
package com.example.khushi_baby_assignemnt.ui.fragments.nowplaying.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.khushi_baby_assignemnt.data.model.MovieDisplayResponse
import com.example.khushi_baby_assignemnt.ui.fragments.nowplaying.viewmodel.NowPlayingRepository
import kotlinx.coroutines.launch

class NowPlayingViewModel(private val repository: NowPlayingRepository) : ViewModel() {

    private val _nowPlayingMovies = MutableLiveData<List<MovieDisplayResponse>>()
    val nowPlayingMovies: LiveData<List<MovieDisplayResponse>> get() = _nowPlayingMovies

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    init {
        fetchNowPlayingMovies()
    }

    fun fetchNowPlayingMovies() {
        viewModelScope.launch {
            try {
                val response = repository.getNowPlayingMovies(1)
                if (response.isSuccessful) {
                    response.body()?.let { _nowPlayingMovies.postValue(it.results) }
                } else {
                    _error.postValue("Failed to fetch now playing movies: ${response.message()}")
                }
            } catch (e: Exception) {
                _error.postValue("Error occurred: ${e.message}")
            }
        }
    }
}
