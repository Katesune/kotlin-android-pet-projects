package com.katesune.data.api.loader

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinsApi {

    @GET("coins/markets")
    suspend fun getMarketCoinsData(
        @Query("vs_currency") currency: String
    ): Response<MarketCoinsResponse>

    @GET("coins/{id}")
    suspend fun getCoinByIdData(
        @Path("id") id: String
    ): Response<CoinResponse>
}