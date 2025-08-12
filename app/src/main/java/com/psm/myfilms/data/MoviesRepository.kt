package com.psm.myfilms.data

import com.psm.myfilms.data.data_sources.MoviesLocalDataSource
import com.psm.myfilms.data.data_sources.remote.MoviesRemoteDataSource

class MoviesRepository(
    private val regionRepository: RegionRepository,
    private val remoteDataSource: MoviesRemoteDataSource,
    private val localDataSource: MoviesLocalDataSource
) {

    suspend fun fetchPopularMovies(): List<Movie> {
        if (localDataSource.isEmpty()) {
            val region = regionRepository.findLastRegion()
            val movies = remoteDataSource.fetchPopularMovies(region)
            localDataSource.save(movies)
        }
        return localDataSource.fetchPopularMovies()
    }

    suspend fun fetchMovieById(id: Int): Movie {
        if (localDataSource.findById(id) == null) {
            val movie = remoteDataSource.fetchMovieById(id)
            localDataSource.save(listOf(movie))
        }
        return checkNotNull(localDataSource.findById(id))
    }

}