package com.example.filmograf.data.api

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

private const val API_KEY = "#"

abstract class InterceptorWithProps : Interceptor {

    abstract val queryProps: Map<String, String>
    override fun intercept(chain: Interceptor.Chain): Response {
        val initialRequest: Request = chain.request()

        val urlWithProps = initialRequest.url().newBuilder()
            .setQueryPropsInUrl()
            .build()

        val requestWithProps: Request = initialRequest.newBuilder()
            .addHeader("Accept", "application/json")
            .addHeader("X-API-KEY", API_KEY)
            .url(urlWithProps)
            .build()

        return chain.proceed(requestWithProps)
    }

    private fun HttpUrl.Builder.setQueryPropsInUrl(): HttpUrl.Builder {
        for (queryProp in queryProps) {
            this.addQueryParameter(queryProp.key, queryProp.value)
        }
        return this
    }
}