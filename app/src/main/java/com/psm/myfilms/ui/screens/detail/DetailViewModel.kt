package com.psm.myfilms.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.psm.myfilms.data.Movie
import com.psm.myfilms.data.MoviesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed interface DetailAction {
    data object FavoriteClicked : DetailAction
    data object MessageShown : DetailAction
}

class DetailViewModel(
    private val repository: MoviesRepository,
    movieId: Int
) : ViewModel() {

    data class UiState(
        val loading: Boolean = false,
        val movie: Movie? = null,
        val message: String? = null
    )

    val state: StateFlow<UiState> = repository.fetchMovieById(movieId)
        .map { UiState(movie = it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState(loading = true)
        )

    fun onAction(action: DetailAction) {
        when (action) {
            is DetailAction.FavoriteClicked -> state.value.movie?.let {
                viewModelScope.launch { repository.toggleFavorite(it) }
            }

            is DetailAction.MessageShown -> {
//                state.update { it.copy(message = null) } // Antes había un MutableStateFlow.
            }
        }
    }

}