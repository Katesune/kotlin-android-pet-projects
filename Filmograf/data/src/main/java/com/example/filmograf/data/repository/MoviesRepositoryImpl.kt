package com.example.filmograf.data.repository

import android.graphics.Bitmap
import com.example.filmograf.data.api.MoviesLoader
import com.example.filmograf.data.api.models.MovieDataListConverted
import com.example.filmograf.domain.repository.MoviesRepository
import com.example.filmograf.domain.models.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val TAG = "MoviesRepositoryImpl"

class MoviesRepositoryImpl(private val moviesLoader: MoviesLoader
) : com.example.filmograf.domain.repository.MoviesRepository, MovieDataListConverted {

    override suspend fun searchMovieById(id: Int): Flow<List<com.example.filmograf.domain.models.Movie>> {
        val searchResponse = moviesLoader.searchById(id = id)
        return moviesLoader.getData(searchResponse)
            .map { moviesData ->
                moviesData.toMovies()
        }
    }

    override suspend fun searchMoviesByQuery(query: String): Flow<List<com.example.filmograf.domain.models.Movie>> {
        val searchResponse = moviesLoader.searchByQuery(query = query)
        return moviesLoader.getData(searchResponse).map { moviesData ->
            moviesData.toMovies()
        }
    }

    override suspend fun loadImage(url: String): Bitmap? =
        moviesLoader.loadImage(url)
}