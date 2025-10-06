package com.psm.myfilms.usecases

import com.psm.myfilms.data.MoviesRepository
import com.psm.myfilms.domain.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {

    operator fun invoke(): Flow<List<Movie>> = moviesRepository.movies

}