package com.katesune.filmograf.domain.usecase

import com.katesune.filmograf.domain.models.Movie
import com.katesune.filmograf.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow

class GetRandomMovie(private val moviesRepository: MoviesRepository) {

    suspend fun invoke(): Flow<List<Movie>> {
        return moviesRepository.getRandomMovie()
    }

}