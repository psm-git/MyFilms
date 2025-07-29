package com.psm.myfilms.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.psm.myfilms.data.Movie
import com.psm.myfilms.data.MoviesRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val movieId: Int) : ViewModel() {

    data class UiState(
        val loading: Boolean = false,
        val movie: Movie? = null
    )

    sealed interface UiEvent {
        data class ShowMessage(val message: String) : UiEvent
    }

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> get() = _state.asStateFlow()

    private val _events = Channel<UiEvent>()
    val events: Flow<UiEvent> get() = _events.receiveAsFlow()

    private val repository = MoviesRepository()

    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            _state.value = UiState(loading = false, movie = repository.fetchMovieById(movieId))
        }
    }

    fun onFavoriteClicked() {
        _events.trySend(UiEvent.ShowMessage("Favorite clicked"))
    }

}