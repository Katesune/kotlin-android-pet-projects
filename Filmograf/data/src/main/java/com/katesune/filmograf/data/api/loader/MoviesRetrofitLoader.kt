package com.katesune.filmograf.data.api.loader

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.katesune.filmograf.data.api.CallResult
import com.katesune.filmograf.data.api.MoviesLoader
import com.katesune.filmograf.data.api.RetrofitResponseHandler
import com.katesune.filmograf.data.api.models.MovieData
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.net.SocketTimeoutException

private const val TAG = "MoviesRetrofitLoader"

class MoviesRetrofitLoader(
    moviesRetrofitService: RetrofitService,
    ) : MoviesLoader {

    private val moviesApi = moviesRetrofitService.moviesApi

    override suspend fun searchById(id: Int): Response<MoviesResponse> {
        return moviesApi.searchMovieById(id = id)
    }

    override suspend fun searchByQuery(query: String): Response<MoviesResponse> {
        return moviesApi.searchMovies(query = query)
    }

    override suspend fun getRandom(): Response<MoviesResponse> {
        return moviesApi.randomMovie()
    }

    override suspend fun getData(response: Response<MoviesResponse>): Flow<List<MovieData>> =
        flow {
            when (val responseResult = RetrofitResponseHandler(response).handleResponse()) {
                is CallResult.Success -> emit(responseResult.response.moviesData)
                else -> responseResult.logErrorsCall()
            }
        }

    override suspend fun loadImage(url: String): Bitmap? =
        withContext(Dispatchers.IO) {

            val imageQuery = async (CoroutineName("QueryForLoadingImage")) {
                try {
                    val response = moviesApi.fetchPoster(url)

                    when (val queryImageResult = RetrofitResponseHandler(response).handleResponse()) {
                        is CallResult.Success -> queryImageResult.response.byteStream().use(BitmapFactory::decodeStream)
                        else -> {
                            queryImageResult.logErrorsCall()
                            null
                        }
                    }
                }
                catch(socketTimeoutException: SocketTimeoutException) {
                    Log.e(TAG, "Image SocketTimeoutException exception")
                    null
                }

            }

            imageQuery.await()
        }
}