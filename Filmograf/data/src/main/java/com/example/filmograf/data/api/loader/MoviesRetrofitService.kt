package com.example.filmograf.data.api.loader

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class RetrofitService {
    abstract val baseUrl: String
    abstract val retrofit: Retrofit
    abstract val moviesApi: MoviesApi
}

class MoviesRetrofitService(client: OkHttpClient, gson: Gson): RetrofitService() {
    override val baseUrl = "https://api.kinopoisk.dev/v1.4/movie/"

    override val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()

    override val moviesApi: MoviesApi by lazy {
        retrofit.create(MoviesApi::class.java)
    }
}