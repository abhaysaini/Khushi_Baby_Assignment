package com.example.khushi_baby_assignemnt.ui.fragments.nowplaying.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.khushi_baby_assignemnt.ui.fragments.nowplaying.viewmodel.NowPlayingRepository
import com.example.khushi_baby_assignemnt.ui.fragments.popular.viewModel.PopularViewModel
import com.example.khushi_baby_assignemnt.ui.fragments.popular.viewmodel.PopularRepository

class NowPlayingViewModelFactory(private val repository: NowPlayingRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NowPlayingViewModel::class.java)) {
            return NowPlayingViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}