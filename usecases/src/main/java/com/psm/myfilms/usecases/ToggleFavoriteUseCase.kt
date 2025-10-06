package com.psm.myfilms.usecases

import com.psm.myfilms.data.MoviesRepository
import com.psm.myfilms.domain.Movie
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {

    suspend operator fun invoke(movie: Movie) {
        moviesRepository.toggleFavorite(movie)
    }

}