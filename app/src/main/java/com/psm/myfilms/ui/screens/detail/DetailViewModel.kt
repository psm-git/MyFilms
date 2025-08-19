package com.psm.myfilms.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.psm.myfilms.Result
import com.psm.myfilms.data.Movie
import com.psm.myfilms.data.MoviesRepository
import com.psm.myfilms.ifSuccess
import com.psm.myfilms.stateAsResultIn
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface DetailAction {
    data object FavoriteClicked : DetailAction
    data object MessageShown : DetailAction
}

class DetailViewModel(
    private val repository: MoviesRepository,
    movieId: Int
) : ViewModel() {

    val state: StateFlow<Result<Movie>> = repository.fetchMovieById(movieId)
        .stateAsResultIn(viewModelScope)

    fun onAction(action: DetailAction) {
        when (action) {
            is DetailAction.FavoriteClicked -> {
                state.value.ifSuccess { movie ->
                    viewModelScope.launch { repository.toggleFavorite(movie) }
                }
            }

            is DetailAction.MessageShown -> {
//                state.update { it.copy(message = null) } // Antes había un MutableStateFlow.
            }
        }
    }

}