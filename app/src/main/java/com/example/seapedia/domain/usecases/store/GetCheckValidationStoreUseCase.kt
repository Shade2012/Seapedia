package com.example.seapedia.domain.usecases.store

import com.example.seapedia.domain.repositories.StoreRepository
import javax.inject.Inject

class GetCheckValidationStoreUseCase
    @Inject constructor(
        private val storeRepository: StoreRepository
    ) {
    suspend fun run() : Boolean{
        return storeRepository.getValidStore()
    }
}