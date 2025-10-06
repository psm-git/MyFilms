package com.psm.myfilms.data

import com.psm.myfilms.data.data_sources.MoviesLocalDataSource
import com.psm.myfilms.data.data_sources.MoviesRemoteDataSource
import com.psm.myfilms.domain.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val regionRepository: RegionRepository,
    private val remoteDataSource: MoviesRemoteDataSource,
    private val localDataSource: MoviesLocalDataSource
) {

    val movies: Flow<List<Movie>> = localDataSource.movies.onEach { localMovies ->
        if (localMovies.isEmpty()) {
            val region = regionRepository.findLastRegion()
            val remoteMovies = remoteDataSource.fetchPopularMovies(region)
            localDataSource.save(remoteMovies)
        }
    }

    fun fetchMovieById(id: Int): Flow<Movie> = localDataSource.findById(id).onEach {
        if (it == null) {
            val movie = remoteDataSource.fetchMovieById(id)
            localDataSource.save(movie)
        }
    }.filterNotNull()

    suspend fun toggleFavorite(movie: Movie) =
        localDataSource.save(movie.copy(isFavorite = !movie.isFavorite))

}