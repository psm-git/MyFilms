package com.psm.myfilms.data

import com.psm.myfilms.data.data_sources.DEFAULT_REGION
import com.psm.myfilms.data.data_sources.MoviesLocalDataSource
import com.psm.myfilms.data.data_sources.MoviesRemoteDataSource
import com.psm.myfilms.domain.Movie
import com.psm.test_fixtures.sampleMovie
import com.psm.test_fixtures.sampleMovies
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.argThat
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class MoviesRepositoryTest {
    @Mock
    private lateinit var regionRepository: RegionRepository

    @Mock
    private lateinit var remoteDataSource: MoviesRemoteDataSource

    @Mock
    private lateinit var localDataSource: MoviesLocalDataSource

    private lateinit var repository: MoviesRepository

    @Before
    fun setUp() {
        repository = MoviesRepository(regionRepository, remoteDataSource, localDataSource)
    }

    @Test
    fun `Popular movies are taken from local data source if available`(): Unit = runBlocking {
        val localMovies = sampleMovies(1, 2, 3)
        whenever(localDataSource.movies).thenReturn(flowOf(localMovies))

        val result = repository.movies

        assertEquals(localMovies, result.first())
    }

    @Test
    fun `Popular movies are saved to local data source when it's empty`(): Unit = runBlocking {
        val localMovies = emptyList<Movie>()
        val remoteMovies = sampleMovies(1, 2)
        whenever(localDataSource.movies).thenReturn(flowOf(localMovies))
        whenever(regionRepository.findLastRegion()).thenReturn(DEFAULT_REGION)
        whenever(remoteDataSource.fetchPopularMovies(DEFAULT_REGION)).thenReturn(remoteMovies)

        repository.movies.first()

        verify(localDataSource).save(remoteMovies)
    }

    @Test
    fun `Toggling favorite updates local data source`(): Unit = runBlocking {
        val movie = sampleMovie(1)

        repository.toggleFavorite(movie)

        verify(localDataSource).save(argThat<Movie> { id == 1 })
    }

    @Test
    fun `Switching favorite marks as favorite`(): Unit = runBlocking {
        val movie = sampleMovie(1).copy(isFavorite = false)

        repository.toggleFavorite(movie)

        verify(localDataSource).save(argThat<Movie> { isFavorite })
    }

    @Test
    fun `Switching favorite unmarks as favorite`(): Unit = runBlocking {
        val movie = sampleMovie(1).copy(isFavorite = true)

        repository.toggleFavorite(movie)

        verify(localDataSource).save(argThat<Movie> { !isFavorite })
    }
}