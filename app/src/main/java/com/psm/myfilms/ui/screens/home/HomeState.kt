package com.psm.myfilms.ui.screens.home

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@OptIn(ExperimentalMaterial3Api::class)
class HomeState(
    val scrollBehavior: TopAppBarScrollBehavior
)

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun rememberHomeState(
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
): HomeState {
    return remember(scrollBehavior) { HomeState(scrollBehavior) }
}