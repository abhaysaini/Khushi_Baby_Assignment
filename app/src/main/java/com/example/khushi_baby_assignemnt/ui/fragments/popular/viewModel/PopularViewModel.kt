// PopularViewModel.kt
package com.example.khushi_baby_assignemnt.ui.fragments.popular.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.khushi_baby_assignemnt.data.model.MovieResponse
import com.example.khushi_baby_assignemnt.ui.fragments.popular.viewmodel.PopularRepository
import kotlinx.coroutines.launch

class PopularViewModel(private val repository: PopularRepository) : ViewModel() {

    private val _popularMovies = MutableLiveData<List<MovieResponse>>()
    val popularMovies: LiveData<List<MovieResponse>> get() = _popularMovies

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    init {
        fetchPopularMovies()
    }

    fun fetchPopularMovies() {
        viewModelScope.launch {
            try {
                val response = repository.getPopularMovies()
                if (response.isSuccessful) {
                    response.body()?.let { _popularMovies.postValue(it.results) }
                } else {
                    _error.postValue("Failed to fetch popular movies: ${response.message()}")
                }
            } catch (e: Exception) {
                _error.postValue("Error occurred: ${e.message}")
            }
        }
    }
}
