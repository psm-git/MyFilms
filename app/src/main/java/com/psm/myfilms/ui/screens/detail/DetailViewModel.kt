package com.psm.myfilms.ui.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.psm.myfilms.domain.Movie
import com.psm.myfilms.ui.Result
import com.psm.myfilms.ui.ifSuccess
import com.psm.myfilms.ui.navigation.NavScreen
import com.psm.myfilms.ui.stateAsResultIn
import com.psm.myfilms.usecases.FindMovieByIdUseCase
import com.psm.myfilms.usecases.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface DetailAction {
    data object FavoriteClicked : DetailAction
    data object MessageShown : DetailAction
}

@HiltViewModel
class DetailViewModel @Inject constructor(
    findMovieByIdUseCase: FindMovieByIdUseCase,
    private val toggleFavoriteIdUseCase: ToggleFavoriteUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val movieId: Int = savedStateHandle.toRoute<NavScreen.Detail>().movieId

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