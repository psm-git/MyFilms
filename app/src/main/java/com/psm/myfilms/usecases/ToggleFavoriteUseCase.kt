package com.psm.myfilms.usecases

import com.psm.myfilms.data.Movie
import com.psm.myfilms.data.MoviesRepository

class ToggleFavoriteUseCase(
    private val moviesRepository: MoviesRepository
) {

    suspend operator fun invoke(movie: Movie) {
        moviesRepository.toggleFavorite(movie)
    }

}