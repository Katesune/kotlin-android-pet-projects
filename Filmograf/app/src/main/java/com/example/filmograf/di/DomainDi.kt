package com.example.filmograf.di

import com.example.filmograf.domain.usecase.GetMoviesImages
import com.example.filmograf.domain.usecase.SearchMovieById
import com.example.filmograf.domain.usecase.SearchMoviesByQuery
import org.koin.dsl.module

val domainModule = module {

    factory<SearchMovieById> {
        SearchMovieById(moviesRepository = get())
    }

    factory<SearchMoviesByQuery> {
        SearchMoviesByQuery(moviesRepository = get())
    }

    factory<GetMoviesImages> {
        GetMoviesImages(moviesRepository = get())
    }

}