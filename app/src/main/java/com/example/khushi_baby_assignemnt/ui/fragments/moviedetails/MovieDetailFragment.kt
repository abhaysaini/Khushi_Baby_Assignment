package com.example.khushi_baby_assignemnt.ui.fragments.moviedetails

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.khushi_baby_assignemnt.BuildConfig
import com.example.khushi_baby_assignemnt.R
import com.example.khushi_baby_assignemnt.data.api.RetrofitHelper
import com.example.khushi_baby_assignemnt.data.model.MovieDetailsResponse
import com.example.khushi_baby_assignemnt.databinding.FragmentMovieDetailBinding
import com.example.khushi_baby_assignemnt.ui.viewmodel.MovieDetailViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {

    lateinit var binding: FragmentMovieDetailBinding
    private val viewModel: MovieDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backButton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        val movieId = requireArguments().getInt("movieId")
        viewModel.fetchMovieDetails(movieId)
        observeMovieDetails()
    }

    private fun observeMovieDetails() {
        viewModel.movieDetails.observe(viewLifecycleOwner) { movieDetails ->
            bindMovieDetails(movieDetails)
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            Log.d("MovieDetailFragment", errorMessage)
        }
    }

    private fun fetchMovieDetails(movieId: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response =
                    RetrofitHelper.responseApiInterface.getMovieDetails(movieId, "Bearer ${BuildConfig.ACCESS_TOKEN_AUTH}")
                response.body()?.let { bindMovieDetails(it) }
                if(response.isSuccessful){

                }
                else{
                    Log.d("abhay", response.message().toString())
                }
            } catch (e: Exception) {
                Log.d("abhay", e.message.toString())
            }
        }
    }

    private fun bindMovieDetails(movieDetails:MovieDetailsResponse) {
        binding.apply {
            // Load image using Glide or another image loading library
            Glide.with(requireContext())
                .load("https://image.tmdb.org/t/p/w500${movieDetails.backdropPath}")
                .into(backdropPath)

            // Set other details
            movieName.text = movieDetails.title
            movieDetail.text = movieDetails.overview
            statusDetails.text = movieDetails.status
            releasedDateDetails.text = movieDetails.releaseDate
            voteAverageDetail.text = movieDetails.voteAverage.toString()

            // Bind genres
            genre.removeAllViews()
            for (genre in movieDetails.genres) {
                val chip = Chip(requireContext())
                chip.text = genre.name
                chip.isClickable = false
                chip.isCheckable = false
                binding.genre.addView(chip)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val view = requireActivity().findViewById<TabLayout>(R.id.tab_layout)
        view.visibility = View.VISIBLE
    }
}