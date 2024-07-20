package com.katesune.filmograf.presentation

private const val SINGE_MOVIE_ROUTE = "movie"
private const val MOVES_ROUTE = "movie"

enum class Destinations(
    val basicRoute: String,
    val route: String,
    val argKey: String
    ) {
    RANDOM(SINGE_MOVIE_ROUTE,"$SINGE_MOVIE_ROUTE/random", ""),
    SEARCH(MOVES_ROUTE,"$MOVES_ROUTE/{query}", "query"),
    ID(SINGE_MOVIE_ROUTE, "$SINGE_MOVIE_ROUTE/{movieId}", "movieId"),
}