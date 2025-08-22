package com.psm.myfilms.usecases

import com.psm.myfilms.data.Movie
import com.psm.myfilms.data.MoviesRepository
import kotlinx.coroutines.flow.Flow

class FetchMoviesUseCase(
    private val moviesRepository: MoviesRepository
) {

    operator fun invoke(): Flow<List<Movie>> = moviesRepository.movies

}