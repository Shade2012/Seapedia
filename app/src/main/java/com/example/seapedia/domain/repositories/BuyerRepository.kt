package com.example.seapedia.domain.repositories

import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.flow.Flow

interface BuyerRepository {
    suspend fun checkValidBuyer(): Flow<CommonState<Boolean>>
}