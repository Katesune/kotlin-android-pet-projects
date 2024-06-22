package com.example.filmograf.domain.usecase

import com.example.filmograf.domain.models.Movie
import com.example.filmograf.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow

private const val TAG = "SearchMoviesByQuery"

class SearchMoviesByQuery (private val moviesRepository: MoviesRepository) {

    suspend fun invoke(query: String): Flow<List<Movie>> {
        return moviesRepository.searchMoviesByQuery(query)
    }

}