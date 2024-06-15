package com.example.filmograf

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.example.filmograf.api.CallResult
import com.example.filmograf.api.MoviesResponse
import com.example.filmograf.api.MoviesApi
import com.example.filmograf.api.MoviesDeserializer
import com.example.filmograf.api.MoviesInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.RuntimeException

private const val TAG = "MoviesCollector"

class MoviesCollector {
    private val moviesApi: MoviesApi

    private val baseUrl = "https://api.kinopoisk.dev/v1.4/movie/"

    init {
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(MoviesInterceptor())
            .build()

        val gson: Gson = GsonBuilder()
            .setLenient()
            .registerTypeAdapter(MoviesResponse::class.java, MoviesDeserializer())
            .create()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()

        moviesApi = retrofit.create(MoviesApi::class.java)
    }

    private fun searchMoviesRequest(query: String): Response<MoviesResponse> {
        return moviesApi.searchMovies(query = query)
    }

    suspend fun searchMovies(query: String): CallResult<MoviesResponse> {
        return collectMoviesData(searchMoviesRequest(query))
    }

    private suspend fun searchMovieRequest(id: Int): Response<MoviesResponse> {
        return moviesApi.searchMovieById(id = id)
    }

    suspend fun searchMovie(id: Int): Flow<List<Movie>> =
        flow {
            val request = searchMovieRequest(id)

            when (val response = collectMoviesData(request)) {
                is CallResult.Success -> emit(response.response.movies)
                is CallResult.Error -> Log.e(TAG, "${response.code} ${response.message}")
                is CallResult.Exception -> Log.e(TAG, response.e.message.toString())
            }
        }

    private fun collectMoviesData(response: Response<MoviesResponse>):
            CallResult<MoviesResponse> {

        return try {
            val body = response.body()
            if (response.isSuccessful && body != null) {
                CallResult.Success(body)
            } else {
                CallResult.Error(code = response.code(), message = response.message())
            }

        } catch (e: HttpException) {
            CallResult.Error(code = response.code(), message = response.message())
        }
        catch(e: Throwable) {
            CallResult.Exception(e)
        }
    }

    fun deliverImages(urls: List<String>) = flow {
        for (url in urls) {
            val image = loadImage(url)
            emit(url to image)
        }
    }

    private suspend fun loadImage(url: String): Bitmap? = withContext(Dispatchers.IO) {

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