package com.katesune.domain.usecase

import com.katesune.domain.models.MarketCoin
import com.katesune.domain.repository.CoinsRepository
import kotlinx.coroutines.flow.Flow

class GetMarketCoins(
    private val coinsRepository: CoinsRepository
) {

    suspend fun invoke(currency: String): Flow<List<MarketCoin>> {
        return coinsRepository.getCoinsMarket(currency = currency)
    }

}
