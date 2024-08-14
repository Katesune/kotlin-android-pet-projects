package com.katesune.data.api.loader

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

private const val API_KEY = "#"

class InterceptorWithKey : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val initialRequest: Request = chain.request()

        val urlWithProps = initialRequest.url().newBuilder()
            .build()

        val requestWithProps: Request = initialRequest.newBuilder()
            .addHeader("Accept", "application/json")
            .addHeader("x-cg-demo-api-key", API_KEY)
            .url(urlWithProps)
            .build()

        return chain.proceed(requestWithProps)
    }
}