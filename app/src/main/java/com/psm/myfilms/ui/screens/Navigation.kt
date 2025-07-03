package com.psm.myfilms.ui.screens

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.psm.myfilms.ui.screens.detail.DetailScreen
import com.psm.myfilms.ui.screens.detail.DetailViewModel
import com.psm.myfilms.ui.screens.home.HomeScreen
import kotlinx.serialization.Serializable

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Home
    ) {
        composable<Home> {
            HomeScreen(
                onMovieClicked = { navController.navigate(Detail(it.id)) }
            )
        }
        composable<Detail> { backStackEntry ->
            val movieId = backStackEntry.toRoute<Detail>().movieId
            DetailScreen(
                viewModel = viewModel { DetailViewModel(movieId) },
                onBackClicked = { navController.popBackStack() }
            )
        }
    }
}

@Serializable
object Home

@Serializable
data class Detail(val movieId: Int)
