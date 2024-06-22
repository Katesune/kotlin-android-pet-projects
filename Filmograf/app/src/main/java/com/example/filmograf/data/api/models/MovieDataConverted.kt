package com.example.filmograf.data.api.models

import com.example.filmograf.domain.models.Movie
import com.example.filmograf.domain.models.Person

internal interface MovieDataListConverted: MovieDataConverted {
    fun List<MovieData>.toMovies(): List<Movie> {
        return this
            .filter { movieData ->
                movieData.title.isNotBlank()
            }
            .map { movieData ->
                movieData.toMovie()
            }
    }
}

internal interface MovieDataConverted {
    fun MovieData.toMovie(): Movie {
        return Movie (
            id = this.id,
            title = this.title,
            year = this.year,
            description = this.description,
            slogan = this.slogan,
            rating = this.rating,
            genres = this.genres,
            posterUri = this.posterUri,
            persons = this.convertPersons(),
            shortDescription = shortDescription
        )
    }

    fun MovieData.convertPersons(): List<Person> {
        return persons.map {
            Person (
                id = it.id,
                name = it.name,
                photoUrl = it.photoUrl
            )
        }
    }
}