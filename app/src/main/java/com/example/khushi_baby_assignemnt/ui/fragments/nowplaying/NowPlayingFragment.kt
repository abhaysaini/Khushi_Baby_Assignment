package com.example.khushi_baby_assignemnt.ui.fragments.nowplaying

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.khushi_baby_assignemnt.R
import com.example.khushi_baby_assignemnt.databinding.FragmentNowPlayingBinding
import com.example.khushi_baby_assignemnt.ui.adapter.NowPlayingAdapter
import com.example.khushi_baby_assignemnt.ui.adapter.OnItemClickListener
import com.example.khushi_baby_assignemnt.ui.fragments.moviedetails.MovieDetailFragment
import com.example.khushi_baby_assignemnt.ui.fragments.nowplaying.viewModel.NowPlayingViewModel
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NowPlayingFragment : Fragment(), OnItemClickListener {

    private lateinit var binding: FragmentNowPlayingBinding
    private val viewModel: NowPlayingViewModel by viewModels()
    private lateinit var adapter: NowPlayingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNowPlayingBinding.inflate(inflater, container, false)
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
            .add(R.id.now_playing, fragment)
            .addToBackStack(null)
            .commit()
    }
}
