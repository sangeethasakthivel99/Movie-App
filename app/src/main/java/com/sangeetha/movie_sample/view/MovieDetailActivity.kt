package com.sangeetha.movie_sample.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.sangeetha.movie_sample.core.Constants
import com.sangeetha.movie_sample.core.Constants.ID
import com.sangeetha.movie_sample.data.Movies
import com.sangeetha.movie_sample.databinding.ActivityMovieDetailBinding
import com.sangeetha.movie_sample.viewmodel.MovieDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding
    private val viewModel: MovieDetailViewModel by viewModels()
    private var movie: Movies? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getIdFromBundle()
        setData()
    }

    private fun getIdFromBundle() {
        val data = intent.extras
        movie = data?.getParcelable(ID)
    }

    private fun setData() {
        movie?.let {
            binding.apply {
                tvPopularity.text = "Popularity: ${it.popularity ?: 0}"
                tvReleaseDate.text = "Release Date: ${it.release_date}"
                tvTitle.text = it.title
                tvDesc.text = it.overview
                ivMovie.load(Constants.PLACE_HOLDER) {
                    crossfade(true)
                    crossfade(1000)
                }
            }
        }
    }
}