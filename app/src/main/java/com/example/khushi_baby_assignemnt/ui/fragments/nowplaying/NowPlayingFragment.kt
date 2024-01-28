package com.example.khushi_baby_assignemnt.ui.fragments.nowplaying

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.khushi_baby_assignemnt.R
import com.example.khushi_baby_assignemnt.data.model.MovieDisplayResponse
import com.example.khushi_baby_assignemnt.databinding.FragmentNowPlayingBinding
import com.example.khushi_baby_assignemnt.ui.adapter.NowPlayingAdapter
import com.example.khushi_baby_assignemnt.ui.adapter.OnItemClickListener
import com.example.khushi_baby_assignemnt.ui.fragments.moviedetails.MovieDetailFragment
import com.example.khushi_baby_assignemnt.ui.fragments.nowplaying.viewModel.NowPlayingViewModel
import com.example.khushi_baby_assignemnt.ui.fragments.nowplaying.viewModel.NowPlayingViewModelFactory
import com.example.khushi_baby_assignemnt.ui.fragments.nowplaying.viewmodel.NowPlayingRepository
import com.google.android.material.tabs.TabLayout

class NowPlayingFragment : Fragment(R.layout.fragment_now_playing), OnItemClickListener {

    lateinit var binding: FragmentNowPlayingBinding
    private val viewModel: NowPlayingViewModel by viewModels{
        NowPlayingViewModelFactory(NowPlayingRepository())
    }
    lateinit var adapter : NowPlayingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNowPlayingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = NowPlayingAdapter(requireContext(), mutableListOf(), this)
        binding.recyclerView.layoutManager = GridLayoutManager(
            requireContext(), 2,
            GridLayoutManager.VERTICAL, false
        )
        binding.recyclerView.adapter = adapter

        viewModel.nowPlayingMovies.observe(viewLifecycleOwner, Observer { movies ->
            movies?.let { setupRecyclerView(it) }
        })
        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            error?.let { Log.e("NowPlayingFragment", it) }
        })

        viewModel.fetchNowPlayingMovies()
    }

    private fun setupRecyclerView(movieList: List<MovieDisplayResponse>) {
        adapter.apply {
            moviesList.clear()
            moviesList.addAll(movieList)
            notifyDataSetChanged()
        }
    }

    override fun onItemClick(movieId: Int) {
        val view = requireActivity().findViewById<TabLayout>(R.id.tab_layout)
        view.visibility = View.GONE
        val fragment = MovieDetailFragment()
        val bundle = Bundle()
        bundle.putInt("movieId", movieId);
        fragment.arguments = bundle
        requireActivity().supportFragmentManager.beginTransaction()
            .add(R.id.now_playing,fragment)
            .addToBackStack(null)
            .commit()
    }
}





//
//        CoroutineScope(Dispatchers.Main).launch {
//            try {
//                val response = RetrofitHelper.responseApiInterface.getMovieNowPlaying(
//                    "Bearer ${BuildConfig.ACCESS_TOKEN_AUTH}"
//                )
//                if (response.isSuccessful) {
//                    Log.d("abhay", response.body().toString())
//                    val movieDisplayList = response.body()?.results
//                    requireActivity().runOnUiThread {
//                        movieDisplayList?.let { setupRecyclerView(it) }
//                    }
//                } else {
//                    Log.d("abhay", response.message().toString())
//                }
//            } catch (e: Exception) {
//                Log.i("abhay", e.message.toString())
//            }
//        }