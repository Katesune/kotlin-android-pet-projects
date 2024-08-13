package com.katesune.data.repository

import com.katesune.data.api.loader.CoinsLoader
import com.katesune.data.api.models.CoinDetailsData
import com.katesune.data.api.models.CoinDetailsDataConverted
import com.katesune.data.api.models.CoinMarketData
import com.katesune.data.api.models.CoinMarketDataConverted
import com.katesune.domain.models.Coin
import com.katesune.domain.repository.CoinsRepository
import com.katesune.domain.models.MarketCoin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CoinsRepositoryImpl(
    private val coinsLoader: CoinsLoader
) : CoinsRepository, CoinMarketDataConverted, CoinDetailsDataConverted {

    override suspend fun getCoinsMarket(currency: String): Flow<List<MarketCoin>> {
        val coinResponse = coinsLoader.getMarketCoinsData(currency = currency)
        return coinsLoader.getCoinsMarketData(coinResponse).convertMarketCoinsDataToMarketCoins()
    }

    override suspend fun getCoinById(id: String): Flow<Coin> {
        val coinResponse = coinsLoader.getCoinDetails(id = id)
        return coinsLoader.getCoinData(coinResponse).convertCoinDetailsDataToCoin()
    }

    private fun Flow<List<CoinMarketData>>.convertMarketCoinsDataToMarketCoins() : Flow<List<MarketCoin>> {
        return this.map { coinMarketData -> coinMarketData.toMarketCoins()}
    }

    private fun Flow<CoinDetailsData>.convertCoinDetailsDataToCoin() : Flow<Coin> {
        return this.map { coinDetailsData -> coinDetailsData.toCoin()}
    }
}