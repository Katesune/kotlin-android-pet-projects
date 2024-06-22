package com.example.filmograf.data.json

import com.example.filmograf.data.api.loader.MoviesResponse
import com.example.filmograf.data.api.models.MovieData
import com.google.gson.*
import java.lang.reflect.Type

class MoviesDeserializer: JsonDeserializer<MoviesResponse>, MoviesJsonParsable {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?)
    : MoviesResponse {

        val movies: List<MovieData> = json?.let {

            val jsonMovies =  it.asJsonObject.get("docs")
            jsonMovies?.unpackDocs() ?: listOf(parseMovieData(it.asJsonObject))

        } ?: listOf()

        return MoviesResponse().apply { this.moviesData = movies }
    }
}