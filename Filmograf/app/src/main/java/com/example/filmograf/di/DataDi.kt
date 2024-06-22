package com.example.filmograf.di

import com.example.filmograf.data.api.MoviesLoader
import com.example.filmograf.data.repository.MoviesRepositoryImpl
import com.example.filmograf.data.api.loader.MoviesRetrofitService
import com.example.filmograf.data.api.loader.RetrofitService
import com.example.filmograf.data.api.loader.MoviesRetrofitLoader
import com.example.filmograf.domain.repository.MoviesRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {

    single<RetrofitService> {
        MoviesRetrofitService (
            client = get(qualifier = named("clientWithInterceptor")),
            gson = get(qualifier = named("gsonWithDeserializer"))
        )
    }

    single<MoviesLoader> {
        MoviesRetrofitLoader(moviesRetrofitService = get())
    }

    single<MoviesRepository> {
        MoviesRepositoryImpl(moviesLoader = get())
    }

}