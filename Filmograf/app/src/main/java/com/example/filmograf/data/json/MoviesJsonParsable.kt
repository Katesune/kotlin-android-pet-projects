package com.example.filmograf.data.json

import com.example.filmograf.data.api.models.MovieData
import com.example.filmograf.data.api.models.PersonData
import com.google.gson.JsonElement
import com.google.gson.JsonObject

internal interface MoviesJsonParsable : MovieJsonParsable {
    fun JsonElement?.unpackDocs(): List<MovieData> {
        return this?.asJsonArray?.map { movie ->
            parseMovieData(movie.asJsonObject)
        } ?: listOf()
    }
}

internal interface MovieJsonParsable : JsonRedirected {
    fun parseMovieData(movie: JsonObject): MovieData {

        return MovieData (
            id = IntJsonProp("id").extract(movie),

            title = StringJsonProp("name").extract(movie),
            year = StringJsonProp("year").extract(movie),
            description = StringJsonProp("description").extract(movie),
            slogan = StringJsonProp("slogan").extract(movie),
            posterUri = StringJsonProp("url").extract(movie.extractObject("poster")),

            rating = FloatJsonProp("kp").extract(movie.extractObject("rating")) ,

            genres = movie.extractArray("genres").map {
                StringJsonProp("name").extract(it.asJsonObject)
            },

            persons = movie.extractArray("persons").map {
                it.asJsonObject?.parsePerson() ?: PersonData()
            },
        )
    }

    private fun JsonObject.parsePerson(): PersonData {
        return PersonData(
            id = IntJsonProp("id").extract(this),
            name = StringJsonProp("name").extract(this),
            photoUrl = StringJsonProp("photo").extract(this),
        )
    }
}