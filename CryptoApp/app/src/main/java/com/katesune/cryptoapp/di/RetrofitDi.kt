package com.katesune.cryptoapp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.katesune.data.api.CoinDetailsDeserializer
import com.katesune.data.api.MarketCoinsDeserializer
import com.katesune.data.api.loader.CoinResponse
import com.katesune.data.api.loader.InterceptorWithKey
import com.katesune.data.api.loader.MarketCoinsResponse
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module

val retrofitModule = module {

    single<OkHttpClient> (named("clientWithInterceptor")) {
        OkHttpClient.Builder()
            .addInterceptor(InterceptorWithKey())
            .build()
    }

    single<Gson> (named("gsonWithDeserializerMarketCoins")) {
        GsonBuilder()
            .setLenient()
            .registerTypeAdapter(
                MarketCoinsResponse::class.java,
                MarketCoinsDeserializer()
            )
            .registerTypeAdapter(
                CoinResponse::class.java,
                CoinDetailsDeserializer()
            )
            .create()
    }
}