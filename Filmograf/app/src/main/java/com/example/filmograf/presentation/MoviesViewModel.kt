package com.example.filmograf.presentation

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmograf.domain.usecase.GetMoviesImages
import com.example.filmograf.domain.usecase.SearchMovieById
import com.example.filmograf.domain.usecase.SearchMoviesByQuery
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "MoviesViewModel"

class MoviesViewModel(
    private val searchMovieById: SearchMovieById,
    private val searchMoviesByQuery: SearchMoviesByQuery,
    private val getMoviesImages: GetMoviesImages
)
    : ViewModel() {

    private var _movies =
        MutableStateFlow<List<com.example.filmograf.domain.models.Movie>>(listOf())
    val movies: StateFlow<List<com.example.filmograf.domain.models.Movie>> = _movies.asStateFlow()

    private val _imageCollection = MutableStateFlow<Map<String, Bitmap?>>(mapOf())
    val imageCollection: StateFlow<Map<String, Bitmap?>> = _imageCollection.asStateFlow()

    fun collectMovies() {
        viewModelScope.launch {
            searchMovieById.invoke(666)
                .collect { movies ->
                _movies.value += movies
                prepareImages()
            }
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
                    _imageCollection.value += it
                }
        }
    }
}