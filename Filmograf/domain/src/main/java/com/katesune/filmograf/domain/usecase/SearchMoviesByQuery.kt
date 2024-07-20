package com.katesune.filmograf.domain.usecase

import com.katesune.filmograf.domain.models.Movie
import com.katesune.filmograf.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow

class SearchMoviesByQuery (private val moviesRepository: MoviesRepository) {

    suspend fun invoke(query: String): Flow<List<Movie>> {
        return moviesRepository.searchMoviesByQuery(query)
    }

}