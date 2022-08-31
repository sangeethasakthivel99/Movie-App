package com.sangeetha.movie_sample.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieRemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRemoteKeys(list: List<MovieRemoteKey>)

    @Query("SELECT * FROM movieremotekey WHERE id = :id")
    suspend fun getAllRemoteKeys(id: Int): MovieRemoteKey?

    @Query("DELETE FROM movieremotekey")
    suspend fun deleteAllRemoteKeys()
}