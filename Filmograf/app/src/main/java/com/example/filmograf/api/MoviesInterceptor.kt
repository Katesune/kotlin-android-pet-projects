package com.example.filmograf.api

import android.util.Log
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

private const val API_KEY = "#"

class MoviesInterceptor: Interceptor {

    private val selectFields = listOf(
        "id", "name", "description", "shortDescription", "slogan", "year", "rating", "genres", "poster", "persons"
    )

    override fun intercept(chain: Interceptor.Chain): Response {

        val initialRequest: Request = chain.request()

        val urlWithProps: HttpUrl = initialRequest.url().newBuilder()
            .addQueryParameter("selectFields", selectFields.toString())
            .build()

        val requestWithProps: Request = initialRequest.newBuilder()
            .addHeader("Accept", "application/json")
            .addHeader("X-API-KEY", API_KEY)
            .url(urlWithProps.url())
            .build()

        Log.d("request", requestWithProps.url().toString())

        return chain.proceed(requestWithProps)
    }

}