package com.sangeetha.movie_sample.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sangeetha.movie_sample.data.Movies
import com.sangeetha.movie_sample.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository
): ViewModel() {
    var movieDetail: MutableLiveData<Movies> = MutableLiveData()

    fun getMovie(id: Int) {
        Log.e("TAG", "getMovie: $id" )
        val movie = movieRepository.getMovie(id)
        movieDetail.value = movie.value
    }
}