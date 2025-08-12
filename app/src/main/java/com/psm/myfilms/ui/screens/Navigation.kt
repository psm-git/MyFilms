package com.psm.myfilms.ui.screens

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.psm.myfilms.data.MoviesRepository
import com.psm.myfilms.data.RegionRepository
import com.psm.myfilms.data.data_sources.LocationDataSource
import com.psm.myfilms.data.data_sources.MoviesRemoteDataSource
import com.psm.myfilms.data.data_sources.RegionDataSource
import com.psm.myfilms.ui.screens.detail.DetailScreen
import com.psm.myfilms.ui.screens.detail.DetailViewModel
import com.psm.myfilms.ui.screens.home.HomeScreen
import com.psm.myfilms.ui.screens.home.HomeViewModel
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
    val app = LocalContext.current.applicationContext as Application
    val moviesRepository = MoviesRepository(
        RegionRepository(RegionDataSource(app, LocationDataSource(app))),
        MoviesRemoteDataSource()
    )

    NavHost(
        navController = navController,
        startDestination = NavScreen.Home
    ) {
        composable<NavScreen.Home> {
            HomeScreen(
                viewModel = viewModel { HomeViewModel(moviesRepository) },
                onMovieClicked = { navController.navigate(NavScreen.Detail(it.id)) }
            )
        }
        composable<NavScreen.Detail> { backStackEntry ->
            val movieId = backStackEntry.toRoute<NavScreen.Detail>().movieId
            DetailScreen(
                viewModel = viewModel { DetailViewModel(moviesRepository, movieId) },
                onBackClicked = { navController.popBackStack() }
            )
        }
    }
}