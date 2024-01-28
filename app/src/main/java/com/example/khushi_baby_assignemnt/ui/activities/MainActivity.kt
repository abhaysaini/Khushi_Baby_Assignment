package com.example.khushi_baby_assignemnt.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.khushi_baby_assignemnt.BuildConfig
import com.example.khushi_baby_assignemnt.R
import com.example.khushi_baby_assignemnt.data.api.RetrofitHelper
import com.example.khushi_baby_assignemnt.data.model.MovieDisplayResponse
import com.example.khushi_baby_assignemnt.data.model.MovieMapper
import com.example.khushi_baby_assignemnt.data.model.MovieResponse
import com.example.khushi_baby_assignemnt.databinding.ActivityMainBinding
import com.example.khushi_baby_assignemnt.ui.adapter.MovieAdapter
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = RetrofitHelper.responseApiInterface.getMovieNowPlaying(
                    "Bearer ${BuildConfig.ACCESS_TOKEN_AUTH}"
                )
                if (response.isSuccessful) {
                    Log.d("abhay", response.body().toString())
                    val movieDisplayList = response.body()?.results
                    runOnUiThread {
                        movieDisplayList?.let { setupRecyclerView(it) }
                    }
                } else {
                    Log.d("abhay", response.message().toString())
                }



                val response_two = RetrofitHelper.responseApiInterface.getPopularMovies(
                    "Bearer ${BuildConfig.ACCESS_TOKEN_AUTH}"
                )
                if (response.isSuccessful) {
                    val movieList = response.body()?.results
                    Log.i("abhay",movieList.toString())
                    Log.d("abhay", response_two.body().toString())
                } else {
                    Log.d("abhay", response.message().toString())
                }
            } catch (e: Exception) {
                Log.i("abhay", e.message.toString())
            }
        }
    }

    private fun setupRecyclerView(movieList: List<MovieDisplayResponse>) {
        var movieAdapter = MovieAdapter(this, movieList.toMutableList())
        binding.recyclerView.layoutManager = GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false)
        binding.recyclerView.adapter = movieAdapter
        movieAdapter.notifyDataSetChanged()
    }
}