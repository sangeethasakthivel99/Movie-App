package com.sangeetha.movie_sample.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sangeetha.movie_sample.core.Constants.ID
import com.sangeetha.movie_sample.data.Movies
import com.sangeetha.movie_sample.databinding.ActivityMainBinding
import com.sangeetha.movie_sample.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), AdapterClickListeners {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MovieViewModel by viewModels()
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        lifecycleScope.launch {
            viewModel.pager.collect {
                movieAdapter.submitData(it)
            }
        }
    }

    private fun initRecyclerView() {
        movieAdapter = MovieAdapter(this)
        binding.movieRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = movieAdapter
            setHasFixedSize(true)
        }
    }

    override fun clickListeners(movie: Movies) {
        startActivity(Intent(this, MovieDetailActivity::class.java).apply {
            putExtra(ID,movie)
        })
    }
}