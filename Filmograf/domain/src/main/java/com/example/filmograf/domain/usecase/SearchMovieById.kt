package com.example.filmograf.domain.usecase

import com.example.filmograf.domain.models.Movie
import com.example.filmograf.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow

private const val TAG = "SearchMovieById"

class SearchMovieById(private val moviesRepository: MoviesRepository) {

    suspend fun invoke(id: Int): Flow<List<Movie>> {
        return moviesRepository.searchMovieById(id)
    }

}