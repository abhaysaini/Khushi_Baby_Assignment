package com.example.khushi_baby_assignemnt.ui.fragments.popular.viewModel
import PopularViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.khushi_baby_assignemnt.ui.fragments.popular.viewmodel.PopularRepository

class PopularViewModelFactory(private val repository: PopularRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PopularViewModel::class.java)) {
            return PopularViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
