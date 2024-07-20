package com.katesune.filmograf.presentation

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.katesune.filmograf.domain.models.Movie
import com.katesune.filmograf.domain.usecase.GetMoviesImages
import com.katesune.filmograf.domain.usecase.GetRandomMovie
import com.katesune.filmograf.domain.usecase.SearchMovieById
import com.katesune.filmograf.domain.usecase.SearchMoviesByQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "MoviesViewModel"

class MoviesViewModel(
    private val searchMovieById: SearchMovieById,
    private val searchMoviesByQuery: SearchMoviesByQuery,
    private val getRandomMovie: GetRandomMovie,
    getMoviesImages: GetMoviesImages,

) : BasicMoviesViewModel(getMoviesImages) {

    var searchQuery = ""

    init {
        viewModelScope.launch {
            collectMovies(getRandomMovie.invoke())
        }
    }
    fun collectByMovieId(movieId: Int) {
        if (movies.value.count { it.id == movieId } == 0) {
            viewModelScope.launch {
                collectMovies(searchMovieById.invoke(movieId))
            }
        }
    }

    fun collectByQuery(query: String) {
        if (query != searchQuery){
            viewModelScope.launch {
                collectMovies(searchMoviesByQuery.invoke(query))
                clearEmptyMovies()
            }
            searchQuery = query
        }
    }

    fun collectRandomMovie() {
        viewModelScope.launch {
            collectMovies(getRandomMovie.invoke())
        }
    }

}

enum class RequestState {
    NOT_STARTED,
    STARTED,
    COMPLETED,
    CANCELED,
}


abstract class BasicMoviesViewModel (
    private val getMoviesImages: GetMoviesImages,
    ): ViewModel() {

    private var _movies = MutableStateFlow<List<Movie>>(listOf())
    val movies: StateFlow<List<Movie>> = _movies.asStateFlow()

    private val _imageCollection = MutableStateFlow<Map<String, Bitmap?>>(mapOf())
    val imageCollection: StateFlow<Map<String, Bitmap?>> = _imageCollection.asStateFlow()

    var loadMoviesState = mutableStateOf(RequestState.NOT_STARTED)

    fun collectMovies(finalFlow: Flow<List<Movie>>) {

        loadMoviesState.value = RequestState.STARTED

        viewModelScope.launch {
            finalFlow
                .collect { moviesResponse ->
                    _movies.value = moviesResponse
                    prepareImages()
                }
        }.isCompleted.let {
            if (it) {
                loadMoviesState.value = if (movies.value.isEmpty()) RequestState.CANCELED
                else RequestState.COMPLETED
            }
        }
    }

    fun clearEmptyMovies() {
        _movies.value = _movies.value.filter {
            it.title.isNotBlank() &&
                    (it.shortDescription.isNotBlank() || it.description.isNotBlank())
        }
    }

    private fun prepareImages() {
        val imagesUrls = movies.value.flatMap { movie ->
            movie.imagesUrls
        }
        collectImages(imagesUrls)
    }

    private fun collectImages(imagesUrls: List<String>) {
        viewModelScope.launch {
            getMoviesImages.execute(imagesUrls)
                .collect {
                    if (it.second != null) _imageCollection.value += it
                }
        }
    }
}