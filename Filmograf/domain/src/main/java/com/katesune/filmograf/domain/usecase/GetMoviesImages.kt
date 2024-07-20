package com.katesune.filmograf.domain.usecase

import android.graphics.Bitmap
import com.katesune.filmograf.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

private const val TAG = "GetMoviesImages"

class GetMoviesImages(private val moviesRepository: MoviesRepository) {

    fun execute(urls: List<String>): Flow<Pair<String, Bitmap?>> = flow {
        for (url in urls) {
            val image = moviesRepository.loadImage(url)
            emit(url to image)
        }
    }

}