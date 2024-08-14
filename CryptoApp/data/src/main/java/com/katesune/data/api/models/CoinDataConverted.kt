package com.katesune.data.api.models

import com.katesune.domain.models.Coin
import com.katesune.domain.models.MarketCoin

internal interface CoinMarketDataConverted {

    fun List<MarketCoinData>.toMarketCoins(): List<MarketCoin> {
        return this.map { coinMarketData ->
            coinMarketData.toMarketCoin()
        }
    }

    fun MarketCoinData.toMarketCoin(): MarketCoin {
        return MarketCoin(
            id = this.id,
            symbol = this.symbol,
            name = this.name,
            image = this.image,
            current_price = this.current_price,
            price_change_24h = this.price_change_24h,
        )
    }
}

internal interface CoinDetailsDataConverted {
    fun CoinDetailsData.toCoin(): Coin {
        return Coin(
            id = this.id,
            image = this.image,
            symbol = this.symbol,
            name = this.name,
            description = this.description,
            categories = this.categories,
        )
    }
}
