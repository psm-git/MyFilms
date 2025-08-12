package com.psm.myfilms.data.data_sources

import com.psm.myfilms.data.Movie
import com.psm.myfilms.data.data_sources.database.MoviesDao

class MoviesLocalDataSource(private val moviesDao: MoviesDao) {

    suspend fun fetchPopularMovies() = moviesDao.fetchAll()

    suspend fun findById(id: Int) = moviesDao.findById(id)

    suspend fun isEmpty() = moviesDao.count() == 0

    suspend fun save(movies: List<Movie>) = moviesDao.save(movies)

}