package com.psm.myfilms.ui.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.psm.myfilms.R
import com.psm.myfilms.ui.common.Loading
import com.psm.myfilms.ui.screens.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(viewModel: DetailViewModel, onBackClicked: () -> Unit) {
    val state by viewModel.state.collectAsState()
    val detailState = rememberDetailState()

    detailState.ShowMessageEffect(message = state.message) {
        viewModel.onAction(DetailAction.MessageShown)
    }

    Screen {
        Scaffold(
            modifier = Modifier.nestedScroll(detailState.scrollBehavior.nestedScrollConnection),
            topBar = {
                DetailTopBar(
                    state.movie?.title ?: "",
                    detailState.scrollBehavior,
                    onBackClicked
                )
            },
            snackbarHost = { SnackbarHost(detailState.snackbarHostState) },
            floatingActionButton = {
                FloatingActionButton(onClick = { viewModel.onAction(DetailAction.FavoriteClicked) }) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = stringResource(R.string.mark_as_favourite)
                    )
                }
            }
        ) { padding ->
            if (state.loading) {
                Loading(padding = padding)
            }
            state.movie?.let {
                Column(
                    modifier = Modifier
                        .padding(padding)
                        .verticalScroll(rememberScrollState())
                ) {
                    AsyncImage(
                        model = it.backdropPath,
                        contentDescription = it.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16 / 9f)
                    )

                    Text(
                        text = it.overview,
                        modifier = Modifier.padding(16.dp)
                    )

                    Text(
                        text = buildAnnotatedString {
                            Property("Original language", it.originalLanguage)
                            Property("Original title", it.originalTitle)
                            Property("Release date", it.releaseDate)
                            Property("Popularity", it.popularity.toString())
                            Property("Vote average", it.voteAverage.toString(), isEnd = true)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surfaceContainer)
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun DetailTopBar(
    title: String,
    scrollBehavior: TopAppBarScrollBehavior,
    onBackClicked: () -> Unit,
) {
    TopAppBar(
        title = { Text(title) },
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            IconButton(onClick = onBackClicked) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.back),
                )
            }
        }
    )
}

@Composable
private fun AnnotatedString.Builder.Property(name: String, value: String, isEnd: Boolean = false) {
    withStyle(ParagraphStyle(lineHeight = 15.sp)) {
        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
            append("$name: ")
        }
        append(value)
        if (!isEnd) {
            appendLine()
        }
    }
}

