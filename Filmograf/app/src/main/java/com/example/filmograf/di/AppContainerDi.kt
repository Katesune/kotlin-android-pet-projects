package com.example.filmograf.di

import com.example.filmograf.MoviesViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

val appModule = module {
    viewModel<MoviesViewModel>{
        MoviesViewModel(
            searchMovieById = get(),
            searchMoviesByQuery = get(),
            getMoviesImages = get()
        )
    }
}