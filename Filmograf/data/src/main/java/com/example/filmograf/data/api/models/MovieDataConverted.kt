package com.example.filmograf.data.api.models

import com.example.filmograf.domain.models.Movie
import com.example.filmograf.domain.models.Person

internal interface MovieDataListConverted: MovieDataConverted {
    fun List<MovieData>.toMovies(): List<com.example.filmograf.domain.models.Movie> {
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
    fun MovieData.toMovie(): com.example.filmograf.domain.models.Movie {
        return com.example.filmograf.domain.models.Movie(
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

    fun MovieData.convertPersons(): List<com.example.filmograf.domain.models.Person> {
        return persons.map {
            com.example.filmograf.domain.models.Person(
                id = it.id,
                name = it.name,
                photoUrl = it.photoUrl
            )
        }
    }
}