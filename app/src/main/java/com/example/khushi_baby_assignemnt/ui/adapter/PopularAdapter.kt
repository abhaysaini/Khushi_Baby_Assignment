package com.example.khushi_baby_assignemnt.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.khushi_baby_assignemnt.data.model.MovieResponse
import com.example.khushi_baby_assignemnt.databinding.ItemMovieBinding

class PopularAdapter(val context: Context, val moviesList: MutableList<MovieResponse>,private val itemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<PopularAdapter.MovieViewHolder>() {


    lateinit var binding: ItemMovieBinding
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PopularAdapter.MovieViewHolder {
        binding = ItemMovieBinding.inflate(LayoutInflater.from(context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PopularAdapter.MovieViewHolder, position: Int) {
        holder.bind(moviesList[position])

    }

    override fun getItemCount(): Int {
        return moviesList.size
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
                    ratingBar.rating = (movie.vote_average / 2).toFloat()
                }
            }
            itemView.setOnClickListener {
                itemClickListener.onItemClick(movie.id)
            }
        }
    }
}

