package com.example.khushi_baby_assignemnt.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.khushi_baby_assignemnt.data.model.MovieDisplayResponse
import com.example.khushi_baby_assignemnt.databinding.ItemMovieBinding

class NowPlayingAdapter(
    val context: Context,
    val moviesList: MutableList<MovieDisplayResponse>,
    private val itemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<NowPlayingAdapter.MovieViewHolder>() {

    lateinit var binding: ItemMovieBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        binding = ItemMovieBinding.inflate(LayoutInflater.from(context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(moviesList[position])

    }

    override fun getItemCount(): Int {
        return moviesList.size
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
}
