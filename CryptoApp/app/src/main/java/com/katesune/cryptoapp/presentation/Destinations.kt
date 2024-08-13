package com.katesune.cryptoapp.presentation

private const val MARKET_ROUTE = "market_coins"
private const val COIN_ROUTE = "coin"

enum class Destinations(
    val basicRoute: String,
    val route: String,
    val argKey: String
) {
    MARKET_COINS(MARKET_ROUTE,"$MARKET_COINS/{currency}", "currency"),
    COIN(COIN_ROUTE,"$COIN/{coinId}", "coinId"),
}