package com.example.khushi_baby_assignemnt.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.khushi_baby_assignemnt.data.model.MovieDisplayResponse
import com.example.khushi_baby_assignemnt.data.model.MovieResponse
import com.example.khushi_baby_assignemnt.databinding.ItemMovieBinding

class NowPlayingAdapter(
    val context: Context,
    private val itemClickListener: OnItemClickListener
) :
    PagingDataAdapter<MovieDisplayResponse, NowPlayingAdapter.MovieViewHolder>(Comparator) {

    lateinit var binding: ItemMovieBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        binding = ItemMovieBinding.inflate(LayoutInflater.from(context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        movie?.let { holder.bind(it) }
    }

    inner class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieDisplayResponse) {
            itemView.apply {
                Glide.with(context)
                    .load("https://image.tmdb.org/t/p/w500${movie.poster_path}")
                    .into(binding.imageView)

                binding.apply {
                    movieName.text = movie.title
//                    movieYear.text = movie.release_date.toString()
                    ratingBar.rating = (movie.vote_average / 2).toFloat()
                }
            }
            itemView.setOnClickListener {
                // Pass the movie ID to the click listener
                itemClickListener.onItemClick(movie.id)
            }
        }
    }

    object Comparator : DiffUtil.ItemCallback<MovieDisplayResponse>() {
        override fun areItemsTheSame(oldItem: MovieDisplayResponse, newItem: MovieDisplayResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieDisplayResponse, newItem: MovieDisplayResponse): Boolean {
            return oldItem == newItem
        }
    }
}
