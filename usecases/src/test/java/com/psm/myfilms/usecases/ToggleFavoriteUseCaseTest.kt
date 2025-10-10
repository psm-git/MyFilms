package com.psm.myfilms.usecases

import com.psm.myfilms.data.MoviesRepository
import com.psm.test_fixtures.sampleMovie
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class ToggleFavoriteUseCaseTest {
    @Test
    fun `Invoke calls repository`() = runBlocking {
        val movie = sampleMovie(1)
        val repository = mock<MoviesRepository>()
        val useCase = ToggleFavoriteUseCase(repository)

        useCase(movie)

        verify(repository).toggleFavorite(movie)
    }
}