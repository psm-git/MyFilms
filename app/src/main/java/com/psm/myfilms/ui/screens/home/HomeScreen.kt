package com.psm.myfilms.ui.screens.home

import android.Manifest
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.psm.myfilms.R
import com.psm.myfilms.data.Movie
import com.psm.myfilms.ui.common.Loading
import com.psm.myfilms.ui.common.PermissionRequestEffect
import com.psm.myfilms.ui.screens.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onMovieClicked: (Movie) -> Unit
) {
    val state by viewModel.state.collectAsState()
    val homeState = rememberHomeState()

    PermissionRequestEffect(Manifest.permission.ACCESS_COARSE_LOCATION) {
        viewModel.onUiReady()
    }

    Screen {
        Scaffold(
            modifier = Modifier.nestedScroll(homeState.scrollBehavior.nestedScrollConnection),
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(R.string.app_name)) },
                    scrollBehavior = homeState.scrollBehavior
                )
            },
        ) { innerPadding ->
            if (state.loading) {
                Loading(padding = innerPadding)
            }
            MyMoviesList(innerPadding, state.movies, onMovieClicked)
        }
    }
}

@Composable
fun MyMoviesList(
    scaffoldPadding: PaddingValues = PaddingValues(0.dp),
    items: List<Movie> = emptyList(),
    onMovieClicked: (Movie) -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier.padding(horizontal = 16.dp),
        contentPadding = scaffoldPadding,
        columns = GridCells.Adaptive(150.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(items) {
            MovieItem(movie = it, onClick = { onMovieClicked(it) })
        }
    }
}

@Composable
fun MovieItem(movie: Movie, onClick: () -> Unit) {
    Column(modifier = Modifier.clickable(onClick = onClick)) {
        Box {
            AsyncImage(
                model = movie.posterPath,
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f / 3f)
                    .clip(MaterialTheme.shapes.small)
            )
            if (movie.isFavorite) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = stringResource(R.string.favourite),
                    tint = MaterialTheme.colorScheme.inverseOnSurface,
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.TopEnd)
                )
            }
        }
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