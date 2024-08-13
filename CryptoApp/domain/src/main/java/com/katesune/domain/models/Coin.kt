package com.katesune.domain.models

class Coin(
    var id: String,
    var symbol:String = "",
    var name: String = "",
    var description: String = "",
    var categories: List<String> = listOf(),
)