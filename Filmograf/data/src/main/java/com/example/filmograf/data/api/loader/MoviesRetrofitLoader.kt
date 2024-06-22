package com.example.filmograf.data.api.loader

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.example.filmograf.data.api.CallResult
import com.example.filmograf.data.api.MoviesLoader
import com.example.filmograf.data.api.ParsableResponse
import com.example.filmograf.data.api.models.MovieData
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.lang.RuntimeException

private const val TAG = "MoviesRetrofitLoader"

class MoviesRetrofitLoader(
    moviesRetrofitService: RetrofitService,
    ) : MoviesLoader, ParsableResponse {

    private val moviesApi = moviesRetrofitService.moviesApi

    override suspend fun searchById(id: Int): Response<MoviesResponse> {
        return moviesApi.searchMovieById(id = id)
    }

    override suspend fun searchByQuery(query: String): Response<MoviesResponse> {
        return moviesApi.searchMovies(query = query)
    }

    override suspend fun getData(response: Response<MoviesResponse>): Flow<List<MovieData>> =
        flow {
            when (val responseResult = parseResponseData(response)) {
                is CallResult.Success -> emit(responseResult.response.moviesData)
                is CallResult.Error -> Log.e(TAG, "${responseResult.code} ${responseResult.message}")
                is CallResult.Exception -> Log.e(TAG, responseResult.e.message.toString())
            }
        }

    override suspend fun loadImage(url: String): Bitmap? =
        withContext(Dispatchers.IO) {

            val imageQuery = async (CoroutineName("QueryForLoadingImage")) {
                val response = moviesApi.fetchPoster(url)
                response.byteStream().use(BitmapFactory::decodeStream)
            }

            try {
                imageQuery.await()
            } catch (e: RuntimeException){
                Log.e(TAG, e.message.toString())
                null
            }
        }

}