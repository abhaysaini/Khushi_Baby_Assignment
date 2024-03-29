package com.example.khushi_baby_assignemnt.ui.fragments.nowplaying.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.khushi_baby_assignemnt.data.repository.NowPlayingRepository

class NowPlayingViewModelFactory(private val repository: NowPlayingRepository, val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NowPlayingViewModel::class.java)) {
            return NowPlayingViewModel(repository, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}