package com.psm.myfilms.data.data_sources.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Query("SELECT * FROM DbMovie")
    fun fetchAll(): Flow<List<DbMovie>>

    @Query("SELECT * FROM DbMovie WHERE id = :id")
    fun findById(id: Int): Flow<DbMovie?>

    @Query("SELECT COUNT(*) FROM DbMovie")
    suspend fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(movies: List<DbMovie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(movie: DbMovie)

    @Delete
    suspend fun delete(movie: DbMovie)

}