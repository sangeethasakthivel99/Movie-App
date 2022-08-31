package com.sangeetha.movie_sample.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class NowPlayingResponse(
    val page: Int?,
    val results: List<Movies>?,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)

@Entity
@Parcelize
data class Movies(
    @PrimaryKey val id: Int,
    val adult: Boolean?,
    val backdrop_path: String?,
    val original_language: String?,
    val original_title: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val release_date: String?,
    val title: String?,
    val video: Boolean?,
    val vote_average: Double?,
    val vote_count: Int?
): Parcelable