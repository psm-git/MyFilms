package com.psm.myfilms.data

import com.psm.myfilms.data.data_sources.MoviesRemoteDataSource

class MoviesRepository(
    private val regionRepository: RegionRepository,
    private val remoteDataSource: MoviesRemoteDataSource
) {

    suspend fun fetchPopularMovies() =
        remoteDataSource.fetchPopularMovies(regionRepository.findLastRegion())

    suspend fun fetchMovieById(id: Int): Movie = remoteDataSource.fetchMovieById(id)

}