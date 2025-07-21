package com.psm.myfilms.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.psm.myfilms.data.Movie
import com.psm.myfilms.data.MoviesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    data class UiState(
        val loading: Boolean = false,
        val movies: List<Movie> = emptyList()
    )

    private val _state = MutableStateFlow(UiState())

    /* Mejor con getter, ya que en algunos casos si no puede dar errores. Además el bytecode es más
       sencillo. */
    val state get() = _state.asStateFlow()

    private val repository = MoviesRepository()

    fun onUiReady(region: String) {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            _state.value = UiState(loading = false, movies = repository.fetchPopularMovies(region))
        }
    }
}