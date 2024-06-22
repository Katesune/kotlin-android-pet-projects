package com.example.filmograf.di

import com.example.filmograf.domain.usecase.GetMoviesImages
import com.example.filmograf.domain.usecase.SearchMovieById
import com.example.filmograf.domain.usecase.SearchMoviesByQuery
import org.koin.dsl.module

val domainModule = module {

    factory<com.example.filmograf.domain.usecase.SearchMovieById> {
        com.example.filmograf.domain.usecase.SearchMovieById(moviesRepository = get())
    }

    factory<com.example.filmograf.domain.usecase.SearchMoviesByQuery> {
        com.example.filmograf.domain.usecase.SearchMoviesByQuery(moviesRepository = get())
    }

    factory<com.example.filmograf.domain.usecase.GetMoviesImages> {
        com.example.filmograf.domain.usecase.GetMoviesImages(moviesRepository = get())
    }

}