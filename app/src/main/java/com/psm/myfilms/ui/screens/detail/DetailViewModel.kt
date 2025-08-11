package com.psm.myfilms.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.psm.myfilms.data.Movie
import com.psm.myfilms.data.MoviesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface DetailAction {
    data object FavoriteClicked : DetailAction
    data object MessageShown : DetailAction
}

class DetailViewModel(
    private val repository: MoviesRepository,
    private val movieId: Int
) : ViewModel() {

    data class UiState(
        val loading: Boolean = false,
        val movie: Movie? = null,
        val message: String? = null
    )

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> get() = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            _state.value = UiState(loading = false, movie = repository.fetchMovieById(movieId))
        }
    }

    fun onAction(action: DetailAction) {
        when (action) {
            is DetailAction.FavoriteClicked -> _state.update { it.copy(message = "Favorite clicked") }
            is DetailAction.MessageShown -> _state.update { it.copy(message = null) }
        }
    }

}