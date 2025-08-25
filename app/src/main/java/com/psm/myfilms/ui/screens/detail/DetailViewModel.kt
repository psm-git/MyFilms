package com.psm.myfilms.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.psm.myfilms.Result
import com.psm.myfilms.domain.Movie
import com.psm.myfilms.ifSuccess
import com.psm.myfilms.stateAsResultIn
import com.psm.myfilms.usecases.FindMovieByIdUseCase
import com.psm.myfilms.usecases.ToggleFavoriteUseCase
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface DetailAction {
    data object FavoriteClicked : DetailAction
    data object MessageShown : DetailAction
}

class DetailViewModel(
    findMovieByIdUseCase: FindMovieByIdUseCase,
    private val toggleFavoriteIdUseCase: ToggleFavoriteUseCase,
    movieId: Int
) : ViewModel() {

    val state: StateFlow<Result<Movie>> = findMovieByIdUseCase(movieId)
        .stateAsResultIn(viewModelScope)

    fun onAction(action: DetailAction) {
        when (action) {
            is DetailAction.FavoriteClicked -> {
                state.value.ifSuccess { movie ->
                    viewModelScope.launch { toggleFavoriteIdUseCase(movie) }
                }
            }

            is DetailAction.MessageShown -> {
//                state.update { it.copy(message = null) } // Antes había un MutableStateFlow.
            }
        }
    }

}