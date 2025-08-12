package com.psm.myfilms.data.data_sources.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.psm.myfilms.data.Movie

@Dao
interface MoviesDao {

    @Query("SELECT * FROM Movie")
    suspend fun fetchAll(): List<Movie>

    @Query("SELECT * FROM Movie WHERE id = :id")
    suspend fun findById(id: Int): Movie?

    @Query("SELECT COUNT(*) FROM Movie")
    suspend fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(movies: List<Movie>)

    @Delete
    suspend fun delete(movie: Movie)

}