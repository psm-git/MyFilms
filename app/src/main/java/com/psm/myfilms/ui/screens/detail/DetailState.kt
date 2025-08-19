package com.psm.myfilms.ui.screens.detail

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.psm.myfilms.Result
import com.psm.myfilms.data.Movie

@OptIn(ExperimentalMaterial3Api::class)
class DetailState(
    private val state: Result<Movie>,
    val snackbarHostState: SnackbarHostState,
    val scrollBehavior: TopAppBarScrollBehavior
) {

    val movie: Movie?
        get() = (state as? Result.Success)?.data

    val topBarTitle: String
        get() = movie?.title ?: ""

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberDetailState(
    state: Result<Movie>,
    snackbarHostState: SnackbarHostState = SnackbarHostState(),
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
): DetailState {
    return remember(snackbarHostState, scrollBehavior) {
        DetailState(
            state,
            snackbarHostState,
            scrollBehavior
        )
    }
}
