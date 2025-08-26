package com.psm.myfilms.data.data_sources

import com.psm.myfilms.domain.Movie

interface MoviesRemoteDataSource {
    suspend fun fetchPopularMovies(region: String): List<Movie>
    suspend fun fetchMovieById(id: Int): Movie
}