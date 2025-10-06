package com.psm.myfilms.framework.remote

import com.psm.myfilms.data.data_sources.MoviesRemoteDataSource
import com.psm.myfilms.domain.Movie
import javax.inject.Inject

class MoviesRetrofitDataSource @Inject constructor(
    private val moviesService: MoviesService
) : MoviesRemoteDataSource {

    override suspend fun fetchPopularMovies(region: String): List<Movie> =
        moviesService
            .fetchPopularMovies(region)
            .results
            .map { it.toDomainModel() }

    override suspend fun fetchMovieById(id: Int): Movie =
        moviesService
            .fetchMovieById(id)
            .toDomainModel()

}

private fun RemoteMovie.toDomainModel() = Movie(
    id,
    title,
    overview,
    releaseDate,
    "https://image.tmdb.org/t/p/w185/$posterPath",
    backdropPath?.let { "https://image.tmdb.org/t/p/w780/$it" },
    originalLanguage,
    originalTitle,
    popularity,
    voteAverage,
    false
)