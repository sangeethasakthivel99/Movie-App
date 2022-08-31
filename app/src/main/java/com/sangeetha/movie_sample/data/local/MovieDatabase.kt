package com.sangeetha.movie_sample.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sangeetha.movie_sample.data.Movies

@Database(version = 1, entities = [Movies::class, MovieRemoteKey::class])
abstract class MovieDatabase : RoomDatabase() {
    abstract fun getMovieDao(): MovieDao
    abstract fun getMovieRemoteKeyDao(): MovieRemoteKeyDao
}