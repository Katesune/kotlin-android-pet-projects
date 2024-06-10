package com.example.filmograf

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class MoviesViewModel: ViewModel() {
    val moviesLiveData: LiveData<List<Movie>>

    private val moviesCollector = MoviesCollector()

    init {
        moviesLiveData = moviesCollector.searchMovie(666)
    }
}