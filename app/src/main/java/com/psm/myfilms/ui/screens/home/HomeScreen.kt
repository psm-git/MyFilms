package com.psm.myfilms.ui.screens.home

import android.Manifest
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.psm.myfilms.Movie
import com.psm.myfilms.R
import com.psm.myfilms.movies
import com.psm.myfilms.ui.common.PermissionRequestEffect
import com.psm.myfilms.ui.common.getRegion
import com.psm.myfilms.ui.screens.Screen
import kotlinx.coroutines.launch

const val HOME_SCREEN_ROUTE = "home"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onMovieClicked: (Movie) -> Unit) {

    val context = LocalContext.current
    var appBarTitle by remember { mutableStateOf(context.getString(R.string.app_name)) }
    val coroutineScope = rememberCoroutineScope()

    PermissionRequestEffect(permission = Manifest.permission.ACCESS_COARSE_LOCATION) { granted ->
        if (granted) {
            coroutineScope.launch {
                val region = context.getRegion()
                appBarTitle = "$appBarTitle ($region)"
            }
        } else {
            appBarTitle = "$appBarTitle (Permission denied)"
        }
    }

    Screen {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopAppBar(
                    title = { Text(text = appBarTitle) },
                    scrollBehavior = scrollBehavior
                )
            },
        ) { innerPadding ->
            MyMoviesList(innerPadding, onMovieClicked)
        }
    }
}

@Composable
fun MyMoviesList(
    scaffoldPadding: PaddingValues = PaddingValues(0.dp),
    onMovieClicked: (Movie) -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier.padding(horizontal = 16.dp),
        contentPadding = scaffoldPadding,
        columns = GridCells.Adaptive(150.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(movies) {
            MovieItem(movie = it, onClick = { onMovieClicked(it) })
        }
    }
}

@Composable
fun MovieItem(movie: Movie, onClick: () -> Unit) {
    Column(modifier = Modifier.clickable(onClick = onClick)) {
        AsyncImage(
            model = movie.imageUrl,
            contentDescription = movie.title,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f / 3f)
                .clip(MaterialTheme.shapes.small)
        )
        Text(
            maxLines = 1,
            style = MaterialTheme.typography.bodySmall,
            text = movie.title,
            modifier = Modifier
                .padding(4.dp)
                .align(Alignment.CenterHorizontally),
        )
    }
}