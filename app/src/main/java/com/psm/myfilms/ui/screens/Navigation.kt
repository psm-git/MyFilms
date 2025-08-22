package com.psm.myfilms.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.psm.myfilms.App
import com.psm.myfilms.data.MoviesRepository
import com.psm.myfilms.data.RegionRepository
import com.psm.myfilms.data.data_sources.LocationDataSource
import com.psm.myfilms.data.data_sources.MoviesLocalDataSource
import com.psm.myfilms.data.data_sources.RegionDataSource
import com.psm.myfilms.data.data_sources.remote.MoviesRemoteDataSource
import com.psm.myfilms.ui.screens.detail.DetailScreen
import com.psm.myfilms.ui.screens.detail.DetailViewModel
import com.psm.myfilms.ui.screens.home.HomeScreen
import com.psm.myfilms.ui.screens.home.HomeViewModel
import com.psm.myfilms.usecases.FetchMoviesUseCase
import com.psm.myfilms.usecases.FindMovieByIdUseCase
import com.psm.myfilms.usecases.ToggleFavoriteUseCase
import kotlinx.serialization.Serializable

object NavScreen {
    @Serializable
    object Home

    @Serializable
    data class Detail(val movieId: Int)
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val app = LocalContext.current.applicationContext as App
    val moviesRepository = MoviesRepository(
        RegionRepository(RegionDataSource(app, LocationDataSource(app))),
        MoviesRemoteDataSource(),
        MoviesLocalDataSource(app.db.moviesDao())
    )

    NavHost(
        navController = navController,
        startDestination = NavScreen.Home
    ) {
        composable<NavScreen.Home> {
            HomeScreen(
                viewModel = viewModel {
                    HomeViewModel(
                        FetchMoviesUseCase(moviesRepository)
                    )
                },
                onMovieClicked = { navController.navigate(NavScreen.Detail(it.id)) }
            )
        }
        composable<NavScreen.Detail> { backStackEntry ->
            val movieId = backStackEntry.toRoute<NavScreen.Detail>().movieId
            DetailScreen(
                viewModel = viewModel {
                    DetailViewModel(
                        FindMovieByIdUseCase(moviesRepository),
                        ToggleFavoriteUseCase(moviesRepository),
                        movieId
                    )
                },
                onBackClicked = { navController.popBackStack() }
            )
        }
    }
}