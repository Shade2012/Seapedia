package com.example.seapedia.domain.usecases.buyer

import com.example.seapedia.domain.repositories.BuyerRepository
import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckValidBuyerUseCase @Inject constructor(
    private val buyerRepository: BuyerRepository
) {
    suspend fun run(): Flow<CommonState<Boolean>>{
        return buyerRepository.checkValidBuyer()
    }
}