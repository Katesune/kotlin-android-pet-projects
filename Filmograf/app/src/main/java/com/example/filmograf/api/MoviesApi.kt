package com.example.filmograf.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("search")
    fun searchMovies(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10,
        @Query("query") query: String
    ): Call<MoviesResponse>

    @GET("{id}")
    fun searchMovieById (
        @Path("id") id: Int
    ): Call<MoviesResponse>
}