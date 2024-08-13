package com.katesune.data.api.models

class CoinDetailsData (
    var id: String,
    var symbol:String = "",
    var name: String = "",
    var description: String = "",
    var categories: List<String> = listOf(),
)