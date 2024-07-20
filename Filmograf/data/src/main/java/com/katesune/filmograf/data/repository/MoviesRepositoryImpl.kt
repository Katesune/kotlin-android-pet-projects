package com.katesune.filmograf.data.repository

import android.graphics.Bitmap
import android.util.Log
import com.katesune.filmograf.data.api.MoviesLoader
import com.katesune.filmograf.data.api.models.MovieData
import com.katesune.filmograf.data.api.models.MovieDataListConverted
import com.katesune.filmograf.domain.repository.MoviesRepository
import com.katesune.filmograf.domain.models.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val TAG = "MoviesRepositoryImpl"

class MoviesRepositoryImpl(private val moviesLoader: MoviesLoader
) : MoviesRepository, MovieDataListConverted {

    override suspend fun searchMovieById(id: Int): Flow<List<Movie>> {
        val searchResponse = moviesLoader.searchById(id = id)
        return moviesLoader.getData(searchResponse).convertMovieDataToMovies()
    }

    override suspend fun searchMoviesByQuery(query: String): Flow<List<Movie>> {
        val searchResponse = moviesLoader.searchByQuery(query = query)
        return moviesLoader.getData(searchResponse).convertMovieDataToMovies()
    }

    override suspend fun getRandomMovie(): Flow<List<Movie>> {
        val searchResponse = moviesLoader.getRandom()
        return moviesLoader.getData(searchResponse).convertMovieDataToMovies()
    }

    override suspend fun loadImage(url: String): Bitmap? =
        if (url.isNotBlank()) moviesLoader.loadImage(url) else null

    private fun Flow<List<MovieData>>.convertMovieDataToMovies()
    : Flow<List<Movie>> {
        return this.map { moviesData -> moviesData.toMovies()}
    }
}