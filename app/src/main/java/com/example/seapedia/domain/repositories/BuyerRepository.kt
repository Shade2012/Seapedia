package com.example.seapedia.domain.repositories

import com.example.seapedia.data.remote.body.BuyerPhoneNumberBody
import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.flow.Flow

interface BuyerRepository {
    suspend fun checkValidBuyer(): Flow<CommonState<Boolean>>
    suspend fun updateBuyer(body: BuyerPhoneNumberBody): Flow<CommonState<String>>
}