package com.example.seapedia.domain.repositories

import android.net.Uri
import com.example.seapedia.data.remote.body.StoreBody
import com.example.seapedia.domain.entities.Store
import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.flow.Flow

interface StoreRepository {
    suspend fun getValidStore(): Boolean

    suspend fun getStoreBySeller(): Flow<CommonState<Store?>>
    suspend fun createStoreBySeller(storeBody: StoreBody, image: Uri): Flow<CommonState<Store>>

    suspend fun updateStoreBySeller(storeBody: StoreBody, image: Uri?) : Flow<CommonState<Store>>
}