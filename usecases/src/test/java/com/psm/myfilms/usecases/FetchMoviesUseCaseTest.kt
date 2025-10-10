package com.psm.myfilms.usecases

import com.psm.test_fixtures.sampleMovies
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class FetchMoviesUseCaseTest {

    @Test
    fun `Invoke calls repository`() {
        val moviesFlow = flowOf(sampleMovies(1, 6, 12, 3))
        val useCase = FetchMoviesUseCase(
            mock {
                on { movies } doReturn moviesFlow
            }
        )

        val result = useCase()

        assertEquals(moviesFlow, result)
    }
}