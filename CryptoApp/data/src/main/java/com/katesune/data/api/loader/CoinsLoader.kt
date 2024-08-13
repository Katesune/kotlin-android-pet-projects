package com.katesune.data.api.loader

import com.katesune.data.api.CallResult
import com.katesune.data.api.RetrofitResponseHandler
import com.katesune.data.api.models.CoinDetailsData
import com.katesune.data.api.models.CoinMarketData
import retrofit2.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CoinsLoader(
    retrofitService: RetrofitService
) {
    private val coinsApi = retrofitService.coinsApi

    suspend fun getMarketCoinsData(currency: String): Response<CoinsMarketResponse> {
        return coinsApi.getMarketCoinsData(currency = currency)
    }

    suspend fun getCoinDetails(id: String): Response<CoinDetailsResponse> {
        return coinsApi.getCoinByIdData(id = id)
    }

    suspend fun getCoinsMarketData(response: Response<CoinsMarketResponse>): Flow<List<CoinMarketData>> =
        flow {
            val responseResult = RetrofitResponseHandler(response).handleResponse()
            if (responseResult is CallResult.Success) emit(responseResult.response.coinsData)
        }

    suspend fun getCoinData(response: Response<CoinDetailsResponse>): Flow<CoinDetailsData> =
        flow {
            val responseResult = RetrofitResponseHandler(response).handleResponse()
            if (responseResult is CallResult.Success) emit(responseResult.response.coinDetailsData)
        }
}