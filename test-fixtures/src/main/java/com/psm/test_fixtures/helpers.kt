package com.psm.test_fixtures

import com.psm.myfilms.domain.Movie


fun sampleMovie(id: Int) = Movie(
    id = id,
    title = "Title",
    overview = "Overview",
    releaseDate = "08/10/2025",
    posterPath = "",
    backdropPath = "",
    originalTitle = "Original title",
    originalLanguage = "EN",
    popularity = 5.0,
    voteAverage = 5.1,
    isFavorite = false,
)

fun sampleMovies(vararg ids: Int): List<Movie> = ids.map { sampleMovie(it) }