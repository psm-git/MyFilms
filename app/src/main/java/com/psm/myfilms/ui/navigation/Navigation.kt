package com.psm.myfilms.ui.navigation

import android.location.Geocoder
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.google.android.gms.location.LocationServices
import com.psm.myfilms.App
import com.psm.myfilms.data.MoviesRepository
import com.psm.myfilms.data.RegionRepository
import com.psm.myfilms.framework.LocationPlayServicesDataSource
import com.psm.myfilms.framework.RegionGeocoderDataSource
import com.psm.myfilms.framework.database.MoviesRoomDataSource
import com.psm.myfilms.framework.remote.MoviesClient
import com.psm.myfilms.framework.remote.MoviesRetrofitDataSource
import com.psm.myfilms.ui.screens.detail.DetailScreen
import com.psm.myfilms.ui.screens.detail.DetailViewModel
import com.psm.myfilms.ui.screens.home.HomeScreen
import com.psm.myfilms.ui.screens.home.HomeViewModel
import com.psm.myfilms.usecases.FetchMoviesUseCase
import com.psm.myfilms.usecases.FindMovieByIdUseCase
import com.psm.myfilms.usecases.ToggleFavoriteUseCase

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val app = LocalContext.current.applicationContext as App
    val moviesRepository = MoviesRepository(
        RegionRepository(
            RegionGeocoderDataSource(
                Geocoder(app),
                LocationPlayServicesDataSource(
                    LocationServices.getFusedLocationProviderClient(app)
                )
            )
        ),
        MoviesRetrofitDataSource(MoviesClient.instance),
        MoviesRoomDataSource(app.db.moviesDao())
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