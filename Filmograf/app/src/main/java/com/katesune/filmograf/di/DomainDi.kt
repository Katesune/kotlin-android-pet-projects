package com.katesune.filmograf.di

import com.katesune.filmograf.domain.usecase.GetMoviesImages
import com.katesune.filmograf.domain.usecase.GetRandomMovie
import com.katesune.filmograf.domain.usecase.SearchMovieById
import com.katesune.filmograf.domain.usecase.SearchMoviesByQuery
import org.koin.dsl.module

val domainModule = module {

    factory<SearchMovieById> {
        SearchMovieById(moviesRepository = get())
    }

    factory<SearchMoviesByQuery> {
        SearchMoviesByQuery(moviesRepository = get())
    }

    factory<GetRandomMovie> {
        GetRandomMovie(moviesRepository = get())
    }

    factory<GetMoviesImages> {
        GetMoviesImages(moviesRepository = get())
    }

}