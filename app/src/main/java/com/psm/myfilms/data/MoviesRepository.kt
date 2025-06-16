package com.psm.myfilms.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class MoviesRepository {

    suspend fun fetchMovies(): List<Movie> = withContext(Dispatchers.IO) {
        delay(2000)
        (1..100).map {
            Movie(
                id = it,
                title = "Movie $it",
                imageUrl = "https://picsum.photos/200/300?random=$it"
            )
        }
    }

    /* TODO: Temporary function without coroutines for passing the movie to detail screen.
    We don't have yet a way to share the selected movie from home screen to the detail screen.
    So detail screen has to get the movie by getting the list again, and it cannot use coroutines as
    it is passed by the Navigation component. */

    fun getMovies(): List<Movie> {
        return (1..100).map {
            Movie(
                id = it,
                title = "Movie $it",
                imageUrl = "https://picsum.photos/200/300?random=$it"
            )
        }
    }

}