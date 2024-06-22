package com.example.filmograf.domain.repository

import android.graphics.Bitmap
import com.example.filmograf.domain.models.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    suspend fun searchMovieById(id: Int): Flow<List<Movie>>
    suspend fun searchMoviesByQuery(query: String): Flow<List<Movie>>
    suspend fun loadImage(url: String): Bitmap?
}