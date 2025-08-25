package com.psm.myfilms.data.data_sources

import com.psm.myfilms.data.data_sources.database.DbMovie
import com.psm.myfilms.data.data_sources.database.MoviesDao
import com.psm.myfilms.domain.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MoviesLocalDataSource(private val moviesDao: MoviesDao) {

    val movies: Flow<List<Movie>> =
        moviesDao.fetchAll().map { movies -> movies.map { it.toDomainMovie() } }

    fun findById(id: Int): Flow<Movie?> = moviesDao.findById(id).map { it?.toDomainMovie() }

    suspend fun isEmpty() = moviesDao.count() == 0

    suspend fun save(movies: List<Movie>) = moviesDao.save(movies.map { it.toDbMovie() })

    suspend fun save(movie: Movie) = moviesDao.save(movie.toDbMovie())

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