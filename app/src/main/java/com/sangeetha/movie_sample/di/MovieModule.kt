package com.sangeetha.movie_sample.di

import android.app.Application
import androidx.room.Room
import com.sangeetha.movie_sample.core.Constants.BASE_URL
import com.sangeetha.movie_sample.data.local.MovieDao
import com.sangeetha.movie_sample.data.local.MovieDatabase
import com.sangeetha.movie_sample.data.local.MovieRemoteKeyDao
import com.sangeetha.movie_sample.data.remote.MovieService
import com.sangeetha.movie_sample.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MovieModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitService(okHttpClient: OkHttpClient): MovieService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .build()
            .create(MovieService::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieDatabase(app: Application): MovieDatabase {
        return Room.databaseBuilder(
            app.applicationContext,
            MovieDatabase::class.java,
            "movie_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMovieDao(database: MovieDatabase): MovieDao {
        return database.getMovieDao()
    }

    @Provides
    @Singleton
    fun provideMovieRemoteKeyDao(database: MovieDatabase): MovieRemoteKeyDao {
        return database.getMovieRemoteKeyDao()
    }

    @Provides
    @Singleton
    fun provideMovieRepo(service: MovieService, dao: MovieDao): MovieRepository {
        return MovieRepository(service, dao)
    }
}