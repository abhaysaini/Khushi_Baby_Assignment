package com.example.khushi_baby_assignemnt.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.khushi_baby_assignemnt.data.model.MovieResponse
import com.example.khushi_baby_assignemnt.databinding.ItemMovieBinding
import com.example.khushi_baby_assignemnt.utils.NetworkUtils
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class PopularAdapter(
    val context: Context,
    private val itemClickListener: OnItemClickListener
) :
    PagingDataAdapter<MovieResponse, PopularAdapter.MovieViewHolder>(Comparator) {


    lateinit var binding: ItemMovieBinding
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PopularAdapter.MovieViewHolder {
        binding = ItemMovieBinding.inflate(LayoutInflater.from(context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PopularAdapter.MovieViewHolder, position: Int) {
        val movie = getItem(position)
        movie?.let { holder.bind(it) }
    }


    inner class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieResponse) {
            itemView.apply {
                Glide.with(context)
                    .load("https://image.tmdb.org/t/p/w500${movie.poster_path}")
                    .into(binding.imageView)

                binding.apply {
                    movieName.text = movie.title
                    movieYear.text = extractYearFromDate(movie.release_date.toString())
                    ratingBar.rating = (movie.vote_average / 2).toFloat()
                }
            }
            itemView.setOnClickListener {
                if (!NetworkUtils.isNetworkAvailable(context)) {
                    showNoInternetSnackbar()
                }
                else{
                    itemClickListener.onItemClick(movie.id)
                }
            }
        }
    }

    private fun showNoInternetSnackbar() {
        Snackbar.make(binding.root, "No internet connection", Snackbar.LENGTH_LONG).show()
    }


    object Comparator : DiffUtil.ItemCallback<MovieResponse>() {
        override fun areItemsTheSame(oldItem: MovieResponse, newItem: MovieResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieResponse, newItem: MovieResponse): Boolean {
            return oldItem == newItem
        }
    }


    fun extractYearFromDate(releaseDate: String): String {
        val dateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
        val calendar = Calendar.getInstance()
        calendar.time = dateFormat.parse(releaseDate) ?: Date()
        return calendar.get(Calendar.YEAR).toString()
    }
}

