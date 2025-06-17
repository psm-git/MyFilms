package com.psm.myfilms.ui.screens.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.psm.myfilms.data.Movie
import com.psm.myfilms.data.MoviesRepository
import kotlinx.coroutines.launch

class DetailViewModel(private val movieId: Int) : ViewModel() {
    data class UiState(
        val loading: Boolean = false,
        val movie: Movie? = null
    )

    var state by mutableStateOf(UiState())
        private set

    private val repository = MoviesRepository()

    init {
        viewModelScope.launch {
            state = UiState(loading = true)
            state = UiState(loading = false, movie = repository.fetchMovieById(movieId))
        }
    }
}