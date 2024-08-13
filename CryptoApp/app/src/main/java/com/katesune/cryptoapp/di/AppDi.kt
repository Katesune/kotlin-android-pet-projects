package com.katesune.cryptoapp.di

import com.katesune.cryptoapp.presentation.CoinsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel<CoinsViewModel> {
        CoinsViewModel(
            getMarketCoins = get(),
            getCoinById = get(),
        )
    }
}