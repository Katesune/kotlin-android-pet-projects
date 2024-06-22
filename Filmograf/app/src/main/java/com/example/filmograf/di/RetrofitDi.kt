package com.example.filmograf.di

import com.example.filmograf.data.json.MoviesDeserializer
import com.example.filmograf.data.api.loader.MoviesInterceptor
import com.example.filmograf.data.api.loader.MoviesResponse
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module

val retrofitModule = module {

    single<OkHttpClient> (named("clientWithInterceptor")) {
        OkHttpClient.Builder()
            .addInterceptor(MoviesInterceptor())
            .build()
    }

    single<Gson> (named("gsonWithDeserializer")) {
        GsonBuilder()
            .setLenient()
            .registerTypeAdapter(MoviesResponse::class.java, MoviesDeserializer())
            .create()
    }

}