package com.example.filmograf.data.api.loader

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface MoviesApi {

    @GET("search")
    suspend fun searchMovies(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10,
        @Query("query") query: String
    ): Response<MoviesResponse>

    @GET("{id}")
    suspend fun searchMovieById (
        @Path("id") id: Int
    ): Response<MoviesResponse>

    @GET
    suspend fun fetchPoster(@Url url: String): ResponseBody
}