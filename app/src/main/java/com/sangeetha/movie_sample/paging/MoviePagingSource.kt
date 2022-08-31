package com.sangeetha.movie_sample.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sangeetha.movie_sample.core.Constants.STARTING_PAGE_INDEX
import com.sangeetha.movie_sample.data.Movies
import com.sangeetha.movie_sample.data.remote.MovieService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MoviePagingSource @Inject constructor(private val api: MovieService) :
    PagingSource<Int, Movies>() {
    override fun getRefreshKey(state: PagingState<Int, Movies>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movies> {
        return try {
            val position = params.key ?: 1
            val response = api.getNowPlayingMovies(position)
            val movies: List<Movies> = (response.results ?: emptyList()) as List<Movies>
            LoadResult.Page(
                data = movies,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (movies.isEmpty()) null else position
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}
