package com.katesune.data.api.loader

import com.katesune.data.api.models.CoinDetailsData
import com.katesune.data.api.models.CoinMarketData

class CoinsMarketResponse {
    lateinit var coinsData: List<CoinMarketData>
}

class CoinDetailsResponse {
    lateinit var coinDetailsData: CoinDetailsData
}