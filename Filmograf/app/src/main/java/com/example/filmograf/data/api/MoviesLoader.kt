package com.example.filmograf.data.api

import android.graphics.Bitmap
import com.example.filmograf.data.api.loader.MoviesResponse
import com.example.filmograf.data.api.models.MovieData
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface MoviesLoader {
    suspend fun searchById(id: Int): Response<MoviesResponse>
    suspend fun searchByQuery(query: String): Response<MoviesResponse>

    suspend fun getData(response: Response<MoviesResponse>): Flow<List<MovieData>>
    suspend fun loadImage(url: String): Bitmap?
}