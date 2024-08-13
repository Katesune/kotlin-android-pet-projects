package com.katesune.cryptoapp.di

import com.katesune.domain.usecase.GetCoinById
import com.katesune.domain.usecase.GetMarketCoins
import org.koin.dsl.module

val domainModule = module {

    factory<GetMarketCoins> {
        GetMarketCoins(coinsRepository = get())
    }

    factory<GetCoinById> {
        GetCoinById(coinsRepository = get())
    }

}