package com.katesune.filmograf.di

import com.katesune.filmograf.presentation.MoviesViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

val appModule = module {
    viewModel<MoviesViewModel>{
        MoviesViewModel(
            searchMovieById = get(),
            searchMoviesByQuery = get(),
            getRandomMovie = get(),
            getMoviesImages = get()
        )
    }
}