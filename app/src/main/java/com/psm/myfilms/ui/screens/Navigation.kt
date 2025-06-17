package com.psm.myfilms.ui.screens

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.psm.myfilms.ui.screens.detail.DETAIL_SCREEN_ROUTE
import com.psm.myfilms.ui.screens.detail.DetailScreen
import com.psm.myfilms.ui.screens.detail.DetailViewModel
import com.psm.myfilms.ui.screens.home.HOME_SCREEN_ROUTE
import com.psm.myfilms.ui.screens.home.HomeScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = HOME_SCREEN_ROUTE
    ) {
        composable(route = HOME_SCREEN_ROUTE) {
            HomeScreen(
                onMovieClicked = { navController.navigate("$DETAIL_SCREEN_ROUTE/${it.id}") }
            )
        }
        composable(
            route = "$DETAIL_SCREEN_ROUTE/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = requireNotNull(backStackEntry.arguments?.getInt("movieId"))
            DetailScreen(
                viewModel = viewModel { DetailViewModel(movieId) },
                onBackClicked = { navController.popBackStack() }
            )
        }
    }
}