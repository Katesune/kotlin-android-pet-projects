package com.example.filmograf.api

import com.example.filmograf.Movie
import com.example.filmograf.Person
import com.google.gson.*
import java.lang.reflect.Type

class MoviesDeserializer: JsonDeserializer<MoviesResponse>, MovieParsable {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): MoviesResponse {
        val movies: List<Movie> = json?.let {
            val jsonMovies =  it.asJsonObject.get("docs")

            jsonMovies?.asJsonArray?.map { movie ->
                parseMovie(movie.asJsonObject)
            } ?: listOf(parseMovie(it.asJsonObject))

        } ?: listOf()

        return MoviesResponse().apply { this.movies = movies }
    }
}

interface MovieParsable {

    fun parseMovie(film: JsonObject): Movie {
        return Movie (
            id = film.getIntProp("id"),
            title = film.getStringProp("name"),
            year = film.getStringProp("year"),
            description = film.getStringProp("description"),
            slogan = film.getStringProp("slogan"),
            rating = film.getAsJsonObject("rating")?.getFloatProp("kp") ?: 0f,
            genres = film.getAsJsonArray("genres")?.map{ it.asJsonObject.getStringProp("name")} ?: listOf(),
            poster = film.getAsJsonObject("poster").asJsonObject?.getStringProp("url") ?: "",
            persons = film.getAsJsonArray("persons")?.map {
                it.asJsonObject?.parsePerson() ?: Person()
            } ?: listOf()
        )
    }

    private fun JsonObject.getIntProp(prop: String): Int {
        return this.get(prop)?.let { elem ->
            if (elem.isJsonNull) 0
            else elem.asInt
        } ?: 0
    }

    private fun JsonObject.getStringProp(prop: String): String {
        return this.get(prop)?.let { elem ->
            if (elem.isJsonNull) ""
            else elem.asString
        } ?: ""
    }

    private fun JsonObject.getFloatProp(prop: String): Float {
        return this.get(prop)?.let { elem ->
            if (elem.isJsonNull) 0f
            else elem.asFloat
        } ?: 0f
    }

    private fun JsonObject.parsePerson(): Person {
        return Person(
            id = this.getIntProp("id"),
            name = this.getStringProp("name"),
            photo = this.getStringProp("photo")
        )
    }
}