package com.katesune.filmograf.presentation

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.katesune.filmograf.domain.models.Movie
import com.katesune.filmograf.R
import com.katesune.filmograf.ui.theme.Typography

private const val TAG = "GeneralScreens"

private val middlePadding = 16.dp

@Composable
fun MoviesDataRecompose(
    switchToSingleMovie: (Int) -> Unit,
    moviesViewModel: MoviesViewModel,
) {
    val movies = moviesViewModel.movies.collectAsState()
    val pictures = moviesViewModel.imageCollection.collectAsState()

    MoviesImagesRecompose(
        switchToSingleMovie,
        movies.value,
        pictures.value,
        moviesViewModel.loadMoviesState.value
    )
}

@Composable
fun MoviesImagesRecompose(
    switchToSingleMovie: (Int) -> Unit,
    movies: List<Movie>,
    pictures: Map<String, Bitmap?>,
    loadMovieState: RequestState
) {
    when (loadMovieState) {
        RequestState.CANCELED -> EmptyScreen()
        RequestState.COMPLETED ->
            ScreenWithMovies(
                switchToSingleMovie,
                movies,
                pictures
        )
        else -> Loading()
    }
}

@Composable
fun EmptyScreen() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(middlePadding)
    ) {
        Text(
            text = stringResource(id = R.string.empty_movies),
            textAlign = TextAlign.Center,
            style = Typography.titleMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEmptyScreen() {
    EmptyScreen()
}

@Composable
fun ScreenWithMovies(
    switchToSingleMovie: (Int) -> Unit,
    movies: List<Movie>,
    pictures: Map<String, Bitmap?>
) {
    val moviesPareCount = movies.size / 2

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(moviesPareCount) { pareIndex ->
            val index = pareIndex * 2

            val moviesPare = if (movies.getOrNull(index + 1) != null) {
                movies.subList(index, index + 2)
            } else listOf(movies[index])

            val picturesPare = moviesPare.map { pictures.getOrDefault(it.posterUri, null) }

            MoviesRow(
                switchToSingleMovie,
                moviesPare,
                picturesPare,
            )

        }
    }
}

@Composable
fun MovieByIdRecompose(
    moviesViewModel: MoviesViewModel,
    movieId: Int
) {
    val movies = moviesViewModel.movies.collectAsState().value
    val pictures = moviesViewModel.imageCollection.collectAsState()

    val loadMovieState = moviesViewModel.loadMoviesState.value

    val selectedMovie = movies.filter { it.id == movieId }

    ScreenWithSingleMovie(
        selectedMovie.ifEmpty { listOf() },
        pictures.value,
        loadMovieState
    )

}

@Composable
fun SingleMovieDataRecompose(
    moviesViewModel: MoviesViewModel,
) {
    val movies = moviesViewModel.movies.collectAsState()
    val pictures = moviesViewModel.imageCollection.collectAsState()

    val loadMovieState = moviesViewModel.loadMoviesState.value

    ScreenWithSingleMovie(
        movies.value,
        pictures.value,
        loadMovieState
    )
}

@Composable
fun ScreenWithSingleMovie(
    movies: List<Movie>,
    pictures: Map<String, Bitmap?>,
    loadMovieState: RequestState
) {
    when (loadMovieState) {
        RequestState.CANCELED -> EmptyScreen()
        RequestState.COMPLETED -> {
            val movie = movies[0]
            MovieWithDetails(movie, pictures)
        }
        else -> Loading()
    }
}

@Composable
fun Loading() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column{
            Text(
                text = stringResource(id = R.string.loading_movies),
                textAlign = TextAlign.Center,
                style = Typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(middlePadding)
            )
            CircularProgressIndicator(

                modifier = Modifier
                    .fillMaxSize(0.1f)
                    .align(Alignment.CenterHorizontally),

                color = Color.LightGray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingPreview() {
    Loading()
}