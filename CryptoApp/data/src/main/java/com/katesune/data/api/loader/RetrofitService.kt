package com.katesune.data.api.loader

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService(
    client: OkHttpClient,
    gson: Gson,
) {
    private val baseUrl = "https://api.coingecko.com/api/v3/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()

    val coinsApi: CoinsApi by lazy {
        retrofit.create(CoinsApi::class.java)
    }
}