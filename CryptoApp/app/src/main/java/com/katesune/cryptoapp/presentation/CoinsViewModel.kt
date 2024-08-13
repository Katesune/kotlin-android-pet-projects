package com.katesune.cryptoapp.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.katesune.domain.models.Coin
import com.katesune.domain.models.MarketCoin
import com.katesune.domain.usecase.GetCoinById
import com.katesune.domain.usecase.GetMarketCoins
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "CoinsViewModel"

class CoinsViewModel(
    private val getMarketCoins: GetMarketCoins,
    private val getCoinById: GetCoinById,

    ) : ViewModel() {

    private var _coins = MutableStateFlow<List<MarketCoin>>(listOf())
    val coins: StateFlow<List<MarketCoin>> = _coins.asStateFlow()

    private val _coin = MutableStateFlow<Coin>(Coin(""))
    val coin: StateFlow<Coin> = _coin.asStateFlow()

    init {
        viewModelScope.launch {
            getMarketCoins.invoke("usd")
                .collect { marketCoin ->
                    _coins.value = marketCoin
                    Log.d(TAG, marketCoin[0].id)
                }
        }
    }

    fun collectMarketCoins(currency: String) {
        viewModelScope.launch {
            getMarketCoins.invoke(currency = currency)
                .collect { marketCoin ->
                    _coins.value = marketCoin
                }
        }
    }

//    fun collectCoinById(id: String) {
//        viewModelScope.launch {
//            getCoinById.invoke(id = id)
//                .collect { coin ->
//                    _coins.value = coin
//                }
//        }
//    }

}