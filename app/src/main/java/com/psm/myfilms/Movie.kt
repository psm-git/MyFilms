package com.psm.myfilms

data class Movie(
    val id: Int,
    val title: String,
    val imageUrl: String
)

val movies = (1..100).map {
    Movie(
        id = it,
        title = "Movie $it",
        imageUrl = "https://picsum.photos/200/300?random=$it"
    )
}