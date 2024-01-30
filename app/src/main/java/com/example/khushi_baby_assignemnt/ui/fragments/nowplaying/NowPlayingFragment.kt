package com.example.khushi_baby_assignemnt.ui.fragments.nowplaying

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.khushi_baby_assignemnt.R
import com.example.khushi_baby_assignemnt.data.model.MovieDisplayResponse
import com.example.khushi_baby_assignemnt.databinding.FragmentNowPlayingBinding
import com.example.khushi_baby_assignemnt.ui.adapter.NowPlayingAdapter
import com.example.khushi_baby_assignemnt.ui.adapter.OnItemClickListener
import com.example.khushi_baby_assignemnt.ui.adapter.PopularAdapter
import com.example.khushi_baby_assignemnt.ui.fragments.moviedetails.MovieDetailFragment
import com.example.khushi_baby_assignemnt.ui.fragments.nowplaying.viewModel.NowPlayingViewModel
import com.example.khushi_baby_assignemnt.ui.fragments.nowplaying.viewModel.NowPlayingViewModelFactory
import com.example.khushi_baby_assignemnt.ui.fragments.nowplaying.viewmodel.NowPlayingRepository
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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

        adapter = NowPlayingAdapter(requireContext(), this)
        binding.recyclerView.layoutManager = GridLayoutManager(
            requireContext(), 2,
            GridLayoutManager.VERTICAL, false
        )
        binding.recyclerView.adapter = adapter

        viewModel.fetchNowPlayingMovies()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.nowPlayingMovies.collectLatest { pagingData ->
                pagingData?.let { adapter.submitData(it) }
            }
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