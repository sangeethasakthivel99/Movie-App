package com.sangeetha.movie_sample.data.local

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import com.sangeetha.movie_sample.data.Movies

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movies>)

    @Query("SELECT * FROM movies")
    fun getMovies(): PagingSource<Int, Movies>

    @Query("SELECT * FROM movies WHERE id=:id")
    fun getMovie(id: Int): LiveData<Movies>

    @Query("DELETE FROM movies")
    suspend fun deleteMovies()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRemoteKeys(keys: List<MovieRemoteKey>)

    @Query("SELECT * FROM movieremotekey WHERE id=:id")
    suspend fun getMovieRemoteKey(id: Int): MovieRemoteKey

    @Query("DELETE FROM movieremotekey")
    suspend fun deleteMovieRemoteKey()
}