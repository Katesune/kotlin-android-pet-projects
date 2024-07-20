package com.katesune.filmograf.domain.usecase

import com.katesune.filmograf.domain.models.Movie
import com.katesune.filmograf.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow

private const val TAG = "SearchMovieById"

class SearchMovieById(private val moviesRepository: MoviesRepository) {

    suspend fun invoke(id: Int): Flow<List<Movie>> {
        return moviesRepository.searchMovieById(id)
    }

}