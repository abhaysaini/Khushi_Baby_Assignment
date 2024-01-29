import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.khushi_baby_assignemnt.data.api.RetrofitApiInterface
import com.example.khushi_baby_assignemnt.data.model.MovieResponse
import com.example.khushi_baby_assignemnt.ui.fragments.popular.paging.PopularPagingSource
import com.example.khushi_baby_assignemnt.ui.fragments.popular.viewmodel.PopularRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PopularViewModel(private val repository: PopularRepository) : ViewModel() {

    private val _popularMovies = MutableStateFlow<PagingData<MovieResponse>?>(null)
    val popularMovies: StateFlow<PagingData<MovieResponse>?> = _popularMovies

    private val _error = MutableLiveData<String?>(null)
    val error: MutableLiveData<String?> = _error

    fun fetchPopularMovies() {
        viewModelScope.launch {
            try {
                Log.i("paging", "Fetching popular movies in ViewModel")
                val pagingSource = PopularPagingSource(repository)
                val pager = Pager(
                    config = PagingConfig(pageSize = PAGE_SIZE),
                    pagingSourceFactory = { pagingSource }
                )
                pager.flow.cachedIn(viewModelScope).collectLatest { pagingData ->
                    _popularMovies.value = pagingData
                }
            } catch (e: Exception) {
                _error.value = "Error occurred: ${e.message}"
            }
        }
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}


//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import androidx.paging.Pager
//import androidx.paging.PagingConfig
//import androidx.paging.PagingData
//import androidx.paging.cachedIn
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.collectLatest
//import com.example.khushi_baby_assignemnt.data.model.MovieResponse
//import com.example.khushi_baby_assignemnt.ui.fragments.popular.paging.PopularPagingSource
//import com.example.khushi_baby_assignemnt.ui.fragments.popular.viewmodel.PopularRepository
//import kotlinx.coroutines.launch
//
//class PopularViewModel(private val repository: PopularRepository) : ViewModel() {
//
//    private val _popularMovies = MutableStateFlow<PagingData<MovieResponse>?>(null)
//    val popularMovies: StateFlow<PagingData<MovieResponse>?> = _popularMovies
//
//    private val _error = MutableLiveData<String?>(null)
//    val error: MutableLiveData<String?> = _error
//
//    init {
//        fetchPopularMovies()
//    }
//
//    fun fetchPopularMovies() {
//        viewModelScope.launch {
//            try {
//                val pagingSource = PopularPagingSource(repository)
//                val pager = Pager(
//                    config = PagingConfig(pageSize = PAGE_SIZE),
//                    pagingSourceFactory = { pagingSource }
//                )
//
////                pager.flow.cachedIn(viewModelScope)
//
//                pager.flow.collectLatest { pagingData ->
//                    _popularMovies.value = pagingData
//                }
//            } catch (e: Exception) {
//                _error.value = "Error occurred: ${e.message}"
//            }
//        }
//    }
//
//    companion object {
//        private const val PAGE_SIZE = 1
//    }
//}
//
//
//
////package com.example.khushi_baby_assignemnt.ui.fragments.popular.viewModel
////
////import androidx.lifecycle.LiveData
////import androidx.lifecycle.MutableLiveData
////import androidx.lifecycle.ViewModel
////import androidx.lifecycle.viewModelScope
////import com.example.khushi_baby_assignemnt.data.model.MovieResponse
////import com.example.khushi_baby_assignemnt.ui.fragments.popular.viewmodel.PopularRepository
////import kotlinx.coroutines.launch
////
////class PopularViewModel(private val repository: PopularRepository) : ViewModel() {
////
////    private val _popularMovies = MutableLiveData<List<MovieResponse>>()
////    val popularMovies: LiveData<List<MovieResponse>> get() = _popularMovies
////
////    private val _error = MutableLiveData<String>()
////    val error: LiveData<String> get() = _error
////
////    init {
////        fetchPopularMovies()
////    }
////
////    fun fetchPopularMovies() {
////        viewModelScope.launch {
////            try {
////                val response = repository.getPopularMovies(1)
////                if (response.isSuccessful) {
////                    response.body()?.let { _popularMovies.postValue(it.results) }
////                } else {
////                    _error.postValue("Failed to fetch popular movies: ${response.message()}")
////                }
////            } catch (e: Exception) {
////                _error.postValue("Error occurred: ${e.message}")
////            }
////        }
////    }
////}
