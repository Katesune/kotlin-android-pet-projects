package com.katesune.data.api

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.katesune.data.api.loader.CoinsMarketResponse
import com.katesune.data.api.models.CoinMarketData
import java.lang.reflect.Type

private const val TAG = "CoinsDeserializer"

class CoinsDeserializer: JsonDeserializer<CoinsMarketResponse> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?)
            : CoinsMarketResponse {

        val coinsMarketData: List<CoinMarketData> = json?.asJsonArray?.map { jsonCoinsArray ->
            jsonCoinsArray.convertToCoin()
        }?.toList() ?: listOf()

        return CoinsMarketResponse().apply { this.coinsData = coinsMarketData }
    }
}

private fun JsonElement.convertToCoin(): CoinMarketData {

    val coinJsonObject = this.asJsonObject

    return CoinMarketData(
        id = coinJsonObject.get("id").asString,
        symbol = coinJsonObject.get("symbol").asString,
        name = coinJsonObject.get("name").asString,
        image = coinJsonObject.get("image").asString,
        current_price = coinJsonObject.get("current_price").asInt,
        price_change_24h = coinJsonObject.get("price_change_24h").asFloat,
    )
}