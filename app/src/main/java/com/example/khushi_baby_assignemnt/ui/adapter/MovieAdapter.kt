package com.example.khushi_baby_assignemnt.ui.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.khushi_baby_assignemnt.R
import com.example.khushi_baby_assignemnt.data.model.MovieDisplayResponse
import com.example.khushi_baby_assignemnt.data.model.MovieResponse
import com.example.khushi_baby_assignemnt.databinding.ItemMovieBinding
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MovieAdapter(val context: Context, val moviesList: MutableList<MovieDisplayResponse>) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

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
        }

//        private fun getFormattedDate(dateString: Date): String {
//            val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
//            val date = format.parse(dateString.toString())
//            val targetFormat = SimpleDateFormat("yyyy", Locale.getDefault())
//            return targetFormat.format(date)
//        }
    }
}