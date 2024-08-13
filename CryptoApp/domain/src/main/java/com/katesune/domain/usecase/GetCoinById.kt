package com.katesune.domain.usecase

import com.katesune.domain.models.Coin
import com.katesune.domain.repository.CoinsRepository
import kotlinx.coroutines.flow.Flow

class GetCoinById(
    private val coinsRepository: CoinsRepository
) {

    suspend fun invoke(id: String): Flow<Coin> {
        return coinsRepository.getCoinById(id = id)
    }

}