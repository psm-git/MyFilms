package com.psm.myfilms

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.psm.myfilms.ui.theme.MyFilmsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyFilmsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface(modifier = Modifier.padding(innerPadding)) {
                        MyMoviesList()
                    }
                }
            }
        }
    }
}

@Composable
fun MyMoviesList() {
    LazyVerticalGrid(
        modifier = Modifier.padding(16.dp),
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