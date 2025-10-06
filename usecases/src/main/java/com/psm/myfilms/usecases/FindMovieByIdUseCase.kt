package com.psm.myfilms.usecases

import com.psm.myfilms.data.MoviesRepository
import com.psm.myfilms.domain.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FindMovieByIdUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    operator fun invoke(id: Int): Flow<Movie> = moviesRepository.fetchMovieById(id)
}