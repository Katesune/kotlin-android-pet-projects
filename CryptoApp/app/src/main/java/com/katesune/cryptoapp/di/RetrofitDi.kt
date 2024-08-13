package com.katesune.cryptoapp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.katesune.data.api.CoinsDeserializer
import com.katesune.data.api.loader.CoinsMarketResponse
import com.katesune.data.api.loader.InterceptorWithKey
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module

val retrofitModule = module {

    single<OkHttpClient> (named("clientWithInterceptor")) {
        OkHttpClient.Builder()
            .addInterceptor(InterceptorWithKey())
            .build()
    }

    single<Gson> (named("gsonWithDeserializer")) {
        GsonBuilder()
            .setLenient()
            .registerTypeAdapter(
                CoinsMarketResponse::class.java,
                CoinsDeserializer()
            )
            .create()
    }

}