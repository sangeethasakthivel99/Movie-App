package com.sangeetha.movie_sample.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.sangeetha.movie_sample.data.Movies
import com.sangeetha.movie_sample.data.local.MovieDao
import com.sangeetha.movie_sample.data.local.MovieDatabase
import com.sangeetha.movie_sample.data.local.MovieRemoteKey
import com.sangeetha.movie_sample.data.local.MovieRemoteKeyDao
import com.sangeetha.movie_sample.data.remote.MovieService
import java.io.InvalidObjectException

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val database: MovieDatabase,
    private val movieApi: MovieService,
    private val movieDao: MovieDao,
    private val movieRemoteKeysDao: MovieRemoteKeyDao,
    private val initialPage: Int = 1
) : RemoteMediator<Int, Movies>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, Movies>): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.APPEND -> {
                    val remoteKey =
                        getLastRemoteKey(state) ?: throw InvalidObjectException("Invalid Object")
                    remoteKey.next ?: return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.REFRESH -> {
                    val remoteKey = getClosestRemoteKeys(state)
                    remoteKey?.next?.minus(1) ?: initialPage
                }
            }

            val response = movieApi.getNowPlayingMovies(page)
            val endOfPagination = response.results?.size!! < state.config.pageSize
            val responseResults = response.results

            if (response.results.isNotEmpty()) {

                response.let {
                    if (loadType == LoadType.REFRESH) {
                        movieRemoteKeysDao.deleteAllRemoteKeys()
                        movieDao.deleteMovies()
                    }
                    val prev = if (page == initialPage) null else page - 1
                    val next = if (endOfPagination) null else page + 1


                    val list = response.results.map {
                        MovieRemoteKey(it.id, prev, next)
                    }
                    movieRemoteKeysDao.insertAllRemoteKeys(list)
                    movieDao.insertMovies(responseResults)
                }
                MediatorResult.Success(endOfPagination)
            } else {
                MediatorResult.Success(endOfPaginationReached = true)
            }
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getClosestRemoteKeys(state: PagingState<Int, Movies>): MovieRemoteKey? {

        return state.anchorPosition?.let {
            state.closestItemToPosition(it)?.let { movie ->
                movieRemoteKeysDao.getAllRemoteKeys(movie.id)
            }
        }

    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, Movies>): MovieRemoteKey? {
        return state.lastItemOrNull()?.let {
            movieRemoteKeysDao.getAllRemoteKeys(it.id)
        }
    }
}