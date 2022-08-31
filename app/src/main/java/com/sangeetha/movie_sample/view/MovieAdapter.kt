package com.sangeetha.movie_sample.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sangeetha.movie_sample.core.Constants.PLACE_HOLDER
import com.sangeetha.movie_sample.data.Movies
import com.sangeetha.movie_sample.databinding.ItemMovieBinding

class MovieAdapter(private val listeners: AdapterClickListeners) :
    PagingDataAdapter<Movies, MovieAdapter.MovieViewHolder>(diffCallBack) {
    companion object {
        val diffCallBack = object : DiffUtil.ItemCallback<Movies>() {
            override fun areItemsTheSame(oldItem: Movies, newItem: Movies): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movies, newItem: Movies): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.binding.apply {
            tvMovieTitle.text = currentItem?.title
            tvMovieDesc.text = currentItem?.overview
            ivMovie.load(PLACE_HOLDER) {
                crossfade(true)
                crossfade(1000)
            }
            cardView.setOnClickListener {
                currentItem?.let {
                    listeners.clickListeners(movie = it)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class MovieViewHolder(val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root)
}

interface AdapterClickListeners {
    fun clickListeners(movie: Movies)
}