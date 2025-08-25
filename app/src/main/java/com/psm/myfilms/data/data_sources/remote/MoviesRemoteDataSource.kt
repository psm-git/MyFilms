package com.psm.myfilms.data.data_sources.remote

import com.psm.myfilms.domain.Movie

class MoviesRemoteDataSource() {

    suspend fun fetchPopularMovies(region: String): List<Movie> =
        MoviesClient.instance
            .fetchPopularMovies(region)
            .results
            .map { it.toDomainModel() }

    suspend fun fetchMovieById(id: Int): Movie =
        MoviesClient.instance
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