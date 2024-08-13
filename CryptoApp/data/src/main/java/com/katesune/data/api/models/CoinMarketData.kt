package com.katesune.data.api.models

class CoinMarketData(
    var id: String,
    var symbol:String = "",
    var name: String = "",
    var image: String = "",
    var current_price: Int = 0,
    var price_change_24h: Float = 0f,
)