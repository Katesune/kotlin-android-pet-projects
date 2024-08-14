package com.katesune.data.api.loader

import com.katesune.data.api.models.MarketCoinData
import com.katesune.data.api.models.CoinDetailsData

class MarketCoinsResponse {
    lateinit var coinsData: List<MarketCoinData>
}

class CoinResponse {
    lateinit var coinData: CoinDetailsData
}