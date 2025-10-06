package com.psm.myfilms.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.psm.myfilms.ui.screens.detail.DetailScreen
import com.psm.myfilms.ui.screens.home.HomeScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavScreen.Home
    ) {
        composable<NavScreen.Home> {
            HomeScreen(
                onMovieClicked = {
                    navController.navigate(NavScreen.Detail(it.id))
                }
            )
        }
        composable<NavScreen.Detail> {
            DetailScreen(
                onBackClicked = { navController.popBackStack() }
            )
        }
    }
}