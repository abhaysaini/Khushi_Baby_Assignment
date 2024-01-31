package com.example.khushi_baby_assignemnt.ui.fragments.popular

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
import com.example.khushi_baby_assignemnt.data.model.MovieResponse
import com.example.khushi_baby_assignemnt.databinding.FragmentPopularBinding
import com.example.khushi_baby_assignemnt.ui.adapter.OnItemClickListener
import com.example.khushi_baby_assignemnt.ui.adapter.PopularAdapter
import com.example.khushi_baby_assignemnt.ui.fragments.moviedetails.MovieDetailFragment
import com.example.khushi_baby_assignemnt.ui.fragments.popular.viewmodel.PopularRepository
import com.example.khushi_baby_assignemnt.ui.fragments.popular.viewModel.PopularViewModel
import com.example.khushi_baby_assignemnt.ui.fragments.popular.viewModel.PopularViewModelFactory
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PopularFragment : Fragment(R.layout.fragment_popular) ,OnItemClickListener {

    lateinit var binding: FragmentPopularBinding
    private val viewModel: PopularViewModel by viewModels {
        PopularViewModelFactory(PopularRepository(requireContext()))
    }
    lateinit var adapter: PopularAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPopularBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = PopularAdapter(requireContext(), this)
        binding.recyclerView.layoutManager = GridLayoutManager(
            requireContext(), 2,
            GridLayoutManager.VERTICAL, false
        )
        binding.recyclerView.adapter = adapter

        viewModel.fetchPopularMovies()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.popularMovies.collectLatest { pagingData ->
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
            .add(R.id.now_playing, fragment)
            .addToBackStack(null)
            .commit()
    }
}