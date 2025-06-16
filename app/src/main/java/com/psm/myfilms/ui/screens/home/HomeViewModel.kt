package com.psm.myfilms.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.psm.myfilms.data.Movie
import com.psm.myfilms.data.MoviesRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    var state by mutableStateOf(UiState())
        private set

    private val repository = MoviesRepository()

    data class UiState(
        val loading: Boolean = false,
        val movies: List<Movie> = emptyList()
    )

    fun onUiReady() {
        viewModelScope.launch {
            state = UiState(loading = true)
            delay(2000)
            state = UiState(loading = false, movies = repository.fetchMovies())
        }
    }

}