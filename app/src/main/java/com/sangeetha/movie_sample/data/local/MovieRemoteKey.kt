package com.sangeetha.movie_sample.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieRemoteKey(
    @PrimaryKey
    val id: Int,
    val prev: Int?,
    val next: Int?
)
