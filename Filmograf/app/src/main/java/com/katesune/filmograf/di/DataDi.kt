package com.katesune.filmograf.di

import com.katesune.filmograf.data.api.MoviesLoader
import com.katesune.filmograf.data.repository.MoviesRepositoryImpl
import com.katesune.filmograf.data.api.loader.MoviesRetrofitService
import com.katesune.filmograf.data.api.loader.RetrofitService
import com.katesune.filmograf.data.api.loader.MoviesRetrofitLoader
import com.katesune.filmograf.domain.repository.MoviesRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {

    single<RetrofitService> {
        MoviesRetrofitService(
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