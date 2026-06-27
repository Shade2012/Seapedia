package com.example.seapedia.domain.usecases.product

import com.example.seapedia.data.remote.query.AllProductSellerQuery
import com.example.seapedia.domain.entities.ProductEntity
import com.example.seapedia.domain.entities.SellerProductEntity
import com.example.seapedia.domain.repositories.ProductRepository
import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllSellerProductUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend fun run(queries: AllProductSellerQuery): Flow<CommonState<SellerProductEntity>>{
        return productRepository.getAllSellerProduct(queries)
    }
}