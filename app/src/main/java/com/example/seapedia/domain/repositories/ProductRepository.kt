package com.example.seapedia.domain.repositories

import com.example.seapedia.data.remote.query.AllProductQuery
import com.example.seapedia.domain.entities.ProductEntity
import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getAllProduct(queries: AllProductQuery): Flow<CommonState<List<ProductEntity>>>
    suspend fun getDetailProduct(id: Int): Flow<CommonState<ProductEntity>>
}