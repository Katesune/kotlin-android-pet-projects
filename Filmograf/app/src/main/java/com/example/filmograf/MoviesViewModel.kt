package com.example.filmograf

import android.util.Log
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmograf.api.CallResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

private const val TAG = "MoviesViewModel"

class MoviesViewModel: ViewModel() {

    private var _movies = MutableStateFlow<List<Movie>>(listOf())
    val movies: StateFlow<List<Movie>> = _movies.asStateFlow()

    private val _imageCollection = MutableStateFlow<Map<String, Bitmap?>>(mapOf())
    val imageCollection: StateFlow<Map<String, Bitmap?>> = _imageCollection.asStateFlow()

    private val moviesCollector = MoviesCollector()

    fun collectMovies() {
        viewModelScope.launch {
            moviesCollector.searchMovie(666)
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
            moviesCollector.deliverImages(imagesUrls)
                .collect {
                    _imageCollection.value += it
                }
        }
    }
}