package com.katesune.cryptoapp.di

import com.katesune.data.api.loader.CoinsLoader
import com.katesune.data.api.loader.RetrofitService
import com.katesune.data.repository.CoinsRepositoryImpl
import com.katesune.domain.repository.CoinsRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {

    single<RetrofitService> {
        RetrofitService(
            client = get(qualifier = named("clientWithInterceptor")),
            gson = get(qualifier = named("gsonWithDeserializer"))
        )
    }

    single<CoinsLoader> {
        CoinsLoader(retrofitService = get())
    }

    single<CoinsRepository> {
        CoinsRepositoryImpl(coinsLoader = get())
    }

}