package com.example.filmograf

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.filmograf.api.MoviesResponse
import com.example.filmograf.api.MoviesApi
import com.example.filmograf.api.MoviesDeserializer
import com.example.filmograf.api.MoviesInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

    private fun searchMoviesRequest(query: String): Call<MoviesResponse> {
        return moviesApi.searchMovies(query = query)
    }

    private fun searchMovieRequest(id: Int): Call<MoviesResponse> {
        return moviesApi.searchMovieById(id = id)
    }

    fun searchMovies(query: String): LiveData<List<Movie>> {
        return collectMovieData(searchMoviesRequest(query))
    }

    fun searchMovie(id: Int): LiveData<List<Movie>> {
        return collectMovieData(searchMovieRequest(id))
    }

    private fun collectMovieData(request: Call<MoviesResponse>): LiveData<List<Movie>> {
        val responseLiveData: MutableLiveData<List<Movie>> = MutableLiveData()

        request.enqueue(object: Callback<MoviesResponse> {
            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                Log.e(TAG, "Failed to fetch films", t)
            }

            override fun onResponse(call: Call<MoviesResponse>, response: Response<MoviesResponse>) {
                Log.d(TAG, "Response received: ${response.body()}")

                val movieResponse: MoviesResponse? = response.body()
                val movies: List<Movie> = movieResponse?.movies ?: mutableListOf()

                responseLiveData.value = movies
            }
        })

        return responseLiveData
    }
}