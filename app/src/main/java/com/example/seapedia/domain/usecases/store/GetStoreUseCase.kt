package com.example.seapedia.domain.usecases.store

import com.example.seapedia.domain.entities.StoreEntity
import com.example.seapedia.domain.repositories.StoreRepository
import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStoreUseCase @Inject constructor(
    private val storeRepository: StoreRepository
) {
    suspend fun run() : Flow<CommonState<StoreEntity?>>{
        return storeRepository.getStoreBySeller()
    }
}