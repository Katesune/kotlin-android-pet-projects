package com.katesune.domain.repository

import com.katesune.domain.models.Coin
import com.katesune.domain.models.MarketCoin
import kotlinx.coroutines.flow.Flow

interface CoinsRepository {

    suspend fun getCoinsMarket(currency: String): Flow<List<MarketCoin>>

    suspend fun getCoinById(id: String): Flow<Coin>

}
