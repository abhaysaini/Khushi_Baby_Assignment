//package com.example.khushi_baby_assignemnt.ui.fragments.nowplaying.viewModel
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import com.example.khushi_baby_assignemnt.data.repository.NowPlayingRepository
//import javax.inject.Inject
//import javax.inject.Singleton
//
//@Singleton
//class NowPlayingViewModelFactory @Inject constructor(private val repository: NowPlayingRepository) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(NowPlayingViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return NowPlayingViewModel(repository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}
