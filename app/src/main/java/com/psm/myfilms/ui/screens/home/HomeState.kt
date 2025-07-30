package com.psm.myfilms.ui.screens.home

import android.Manifest
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.psm.myfilms.ui.common.PermissionRequestEffect
import com.psm.myfilms.ui.common.getRegion
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
class HomeState(
    val scrollBehavior: TopAppBarScrollBehavior
) {
    @Composable
    fun AskRegionEffect(onRegion: (String) -> Unit) {
        val context = LocalContext.current.applicationContext
        val coroutineScope = rememberCoroutineScope()

        PermissionRequestEffect(permission = Manifest.permission.ACCESS_COARSE_LOCATION) { granted ->
            coroutineScope.launch {
                val region = if (granted) context.getRegion() else "US"
                onRegion(region)
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun rememberHomeState(
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
): HomeState {
    return remember(scrollBehavior) { HomeState(scrollBehavior) }
}