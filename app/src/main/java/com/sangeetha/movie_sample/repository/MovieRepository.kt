package com.sangeetha.movie_sample.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sangeetha.movie_sample.paging.MoviePagingSource
import com.sangeetha.movie_sample.data.Movies
import com.sangeetha.movie_sample.data.local.MovieDao
import com.sangeetha.movie_sample.data.remote.MovieService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val service: MovieService,
    private val dao: MovieDao,
) {
    fun getNowPlayingMovies(): Flow<PagingData<Movies>> {
        return Pager(
            config = PagingConfig(
                enablePlaceholders = false,
                pageSize = 5,
                maxSize = 100
            ),
            pagingSourceFactory = {
                MoviePagingSource(service)
            }
        ).flow
    }

    fun getMovie(id: Int): LiveData<Movies> {
        return dao.getMovie(id)
    }
}