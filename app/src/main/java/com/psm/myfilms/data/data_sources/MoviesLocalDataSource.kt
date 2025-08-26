package com.psm.myfilms.data.data_sources

import com.psm.myfilms.domain.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesLocalDataSource {
    val movies: Flow<List<Movie>>
    fun findById(id: Int): Flow<Movie?>
    suspend fun isEmpty(): Boolean
    suspend fun save(movies: List<Movie>)
    suspend fun save(movie: Movie)
}