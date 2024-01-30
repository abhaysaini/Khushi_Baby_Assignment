//package com.example.khushi_baby_assignemnt.ui.fragments.popular.viewModel
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import com.example.khushi_baby_assignemnt.data.repository.PopularRepository
//import javax.inject.Inject
//import javax.inject.Singleton
//
//@Singleton
//class PopularViewModelFactory @Inject constructor(private val repository: PopularRepository) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(PopularViewModel::class.java)) {
//            return PopularViewModel(repository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}
