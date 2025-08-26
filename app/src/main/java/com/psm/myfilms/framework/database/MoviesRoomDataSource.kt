package com.psm.myfilms.framework.database

import com.psm.myfilms.data.data_sources.MoviesLocalDataSource
import com.psm.myfilms.domain.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MoviesRoomDataSource(private val moviesDao: MoviesDao) : MoviesLocalDataSource {

    override val movies: Flow<List<Movie>> =
        moviesDao.fetchAll().map { movies -> movies.map { it.toDomainMovie() } }

    override fun findById(id: Int): Flow<Movie?> =
        moviesDao.findById(id).map { it?.toDomainMovie() }

    override suspend fun isEmpty() = moviesDao.count() == 0

    override suspend fun save(movies: List<Movie>) = moviesDao.save(movies.map { it.toDbMovie() })

    override suspend fun save(movie: Movie) = moviesDao.save(movie.toDbMovie())

}

private fun DbMovie.toDomainMovie() = Movie(
    id = id,
    title = title,
    overview = overview,
    releaseDate = releaseDate,
    posterPath = posterPath,
    backdropPath = backdropPath,
    originalTitle = originalLanguage,
    originalLanguage = originalTitle,
    popularity = popularity,
    voteAverage = voteAverage,
    isFavorite = isFavorite
)

private fun Movie.toDbMovie() = DbMovie(
    id = id,
    title = title,
    overview = overview,
    releaseDate = releaseDate,
    posterPath = posterPath,
    backdropPath = backdropPath,
    originalTitle = originalLanguage,
    originalLanguage = originalTitle,
    popularity = popularity,
    voteAverage = voteAverage,
    isFavorite = isFavorite
)