package com.example.khushi_baby_assignemnt.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.khushi_baby_assignemnt.data.model.MovieDetailsResponse
import com.example.khushi_baby_assignemnt.data.repository.MovieDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class MovieDetailViewModel : ViewModel() {

    private val movieDetailRepository = MovieDetailRepository()

    private val _movieDetails = MutableLiveData<MovieDetailsResponse>()
    val movieDetails: LiveData<MovieDetailsResponse> = _movieDetails

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun fetchMovieDetails(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = movieDetailRepository.getMovieDetails(movieId)
                if (response.isSuccessful) {
                    _movieDetails.postValue(response.body())
                } else {
                    _error.postValue(response.message())
                }
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }
}
