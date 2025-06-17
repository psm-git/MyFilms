package com.psm.myfilms.data

class MoviesRepository {
    suspend fun fetchPopularMovies(region: String): List<Movie> =
        MoviesClient.instance
            .fetchPopularMovies(region)
            .results
            .map { it.toDomainModel() }

    suspend fun fetchMovieById(id: Int): Movie =
        MoviesClient.instance
            .fetchMovieById(id)
            .toDomainModel()

    private fun RemoteMovie.toDomainModel() = Movie(
        id = id,
        title = title,
        imageUrl = "https://image.tmdb.org/t/p/w500${posterPath}"
    )
}