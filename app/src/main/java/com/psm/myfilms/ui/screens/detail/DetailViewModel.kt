package com.psm.myfilms.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.psm.myfilms.data.Movie
import com.psm.myfilms.data.MoviesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val movieId: Int) : ViewModel() {
    data class UiState(
        val loading: Boolean = false,
        val movie: Movie? = null
    )

    private val _state = MutableStateFlow(UiState())
    val state get() = _state.asStateFlow()

    private val repository = MoviesRepository()

    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            _state.value = UiState(loading = false, movie = repository.fetchMovieById(movieId))
        }
    }
}