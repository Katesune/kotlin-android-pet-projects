package com.katesune.data.api

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.katesune.data.api.loader.CoinResponse
import com.katesune.data.api.loader.MarketCoinsResponse
import com.katesune.data.api.models.MarketCoinData
import com.katesune.data.api.models.CoinDetailsData
import java.lang.reflect.Type

class MarketCoinsDeserializer: JsonDeserializer<MarketCoinsResponse> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?)
            : MarketCoinsResponse {

        val coinsData: List<MarketCoinData> =  json?.asJsonArray?.map { jsonCoinsArray ->
            jsonCoinsArray.asJsonObject.convertToMarketCoin()
        }?.toList() ?: listOf()

        return MarketCoinsResponse().apply { this.coinsData = coinsData }
    }
}

private fun JsonObject.convertToMarketCoin(): MarketCoinData {
    return MarketCoinData(
        id = this.get("id").asString,
        symbol = this.get("symbol").asString,
        name = this.get("name").asString,
        image = this.get("image").asString,
        current_price = this.get("current_price").asInt,
        price_change_24h = this.get("price_change_24h").asFloat,
    )
}

class CoinDetailsDeserializer: JsonDeserializer<CoinResponse> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?)
            : CoinResponse {

        val coinData: CoinDetailsData = json?.asJsonObject?.convertToCoinDetails() ?: CoinDetailsData("")

        return CoinResponse().apply { this.coinData = coinData }
    }
}

private fun JsonObject.convertToCoinDetails(): CoinDetailsData {

    val descriptions = this.get("description").asJsonObject
    val categoriesJsonArray = this.get("categories").asJsonArray
    val image = this.get("image").asJsonObject

    return CoinDetailsData(
        id = this.get("id").asString,
        symbol = this.get("symbol").asString,
        name = this.get("name").asString,
        image = image.get("large").asString,
        description = descriptions.get("en").asString,
        categories = categoriesJsonArray.map { it.asString },
    )
}