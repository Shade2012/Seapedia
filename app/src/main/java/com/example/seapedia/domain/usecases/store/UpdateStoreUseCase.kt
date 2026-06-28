package com.example.seapedia.domain.usecases.store

import android.net.Uri
import com.example.seapedia.data.remote.body.StoreBody
import com.example.seapedia.domain.entities.Store
import com.example.seapedia.domain.repositories.StoreRepository
import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateStoreUseCase @Inject constructor(
    private val storeRepository: StoreRepository
) {
    suspend fun run(storeBody: StoreBody, image: Uri?): Flow<CommonState<Store>> {
        return storeRepository.updateStoreBySeller(storeBody,image)
    }
}