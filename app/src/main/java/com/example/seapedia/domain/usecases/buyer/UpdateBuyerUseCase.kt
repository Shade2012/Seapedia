package com.example.seapedia.domain.usecases.buyer

import com.example.seapedia.data.remote.body.BuyerPhoneNumberBody
import com.example.seapedia.domain.repositories.BuyerRepository
import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateBuyerUseCase @Inject constructor(
    private val buyerRepository: BuyerRepository
) {
    suspend fun run(body: BuyerPhoneNumberBody): Flow<CommonState<String>>{
        return buyerRepository.updateBuyer(body)
    }
}