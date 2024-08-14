package com.katesune.cryptoapp.presentation

import android.util.Log
import androidx.compose.runtime.mutableStateOf
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

    private val _coin = MutableStateFlow(Coin(""))
    val coin: StateFlow<Coin> = _coin.asStateFlow()

    var loadCoinsState = mutableStateOf(RequestState.NOT_STARTED)

    init {
        collectMarketCoins("USD")
    }

    fun collectMarketCoins(currency: String) {

        loadCoinsState.value = RequestState.STARTED

        viewModelScope.launch {
            getMarketCoins.invoke(currency = currency)
                .collect { marketCoin ->
                    _coins.value = marketCoin
                    loadCoinsState.value =
                        if (_coins.value.isEmpty()) RequestState.CANCELED
                        else RequestState.INPROGRESS
                }
        }
    }

    fun collectCoinById(id: String) {

        loadCoinsState.value = RequestState.STARTED

        viewModelScope.launch {
            getCoinById.invoke(id = id)
                .collect { coin ->
                    _coin.value = coin
                    loadCoinsState.value =
                        if (_coin.value.id.isBlank()) RequestState.CANCELED
                        else RequestState.INPROGRESS
                }
        }
    }

}

enum class RequestState {
    NOT_STARTED,
    STARTED,
    INPROGRESS,
    CANCELED,
}