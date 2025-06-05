package com.psm.myfilms.ui.screens.home

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.psm.myfilms.Movie
import com.psm.myfilms.movies
import com.psm.myfilms.ui.screens.Screen
import com.psm.myfilms.ui.theme.MyFilmsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    Screen {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopAppBar(
                    title = { Text("Movies") },
                    scrollBehavior = scrollBehavior
                )
            },
        ) { innerPadding ->
            MyMoviesList(innerPadding)
        }
    }
}

@Composable
fun MyMoviesList(scaffoldPadding: PaddingValues = PaddingValues(0.dp)) {
    LazyVerticalGrid(
        modifier = Modifier.padding(horizontal = 16.dp),
        contentPadding = scaffoldPadding,
        columns = GridCells.Adaptive(150.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(movies) {
            MovieItem(it)
        }
    }
}

@Composable
fun MovieItem(movie: Movie) {
    Column {
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

@Preview(showBackground = true)
@Composable
fun ComposablePreview() {
    MyFilmsTheme {
//       MovieItem(movies[0])
        MyMoviesList()
    }
}