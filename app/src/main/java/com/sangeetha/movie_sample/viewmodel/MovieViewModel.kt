package com.sangeetha.movie_sample.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.sangeetha.movie_sample.data.Movies
import com.sangeetha.movie_sample.data.local.MovieDao
import com.sangeetha.movie_sample.data.local.MovieDatabase
import com.sangeetha.movie_sample.data.local.MovieRemoteKeyDao
import com.sangeetha.movie_sample.data.remote.MovieService
import com.sangeetha.movie_sample.paging.MovieRemoteMediator
import com.sangeetha.movie_sample.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalPagingApi::class)
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val movieDao: MovieDao,
    private val movieRemoteKey: MovieRemoteKeyDao,
    private val database: MovieDatabase,
    private val movieService: MovieService
) : ViewModel() {

    val list: Flow<PagingData<Movies>> = movieRepository.getNowPlayingMovies()

    private val articlesDao = { movieDao.getMovies() }

    val pager: Flow<PagingData<Movies>> = Pager(
        PagingConfig(pageSize = 5),
        remoteMediator =
        MovieRemoteMediator(
            movieApi = movieService,
            movieDao = movieDao,
            database = database,
            movieRemoteKeysDao = movieRemoteKey,
            initialPage = 1
        ),
        pagingSourceFactory = articlesDao

    ).flow.cachedIn(viewModelScope)
}