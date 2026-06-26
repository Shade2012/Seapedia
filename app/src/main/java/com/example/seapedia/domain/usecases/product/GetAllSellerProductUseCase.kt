package com.example.seapedia.domain.usecases.product

import com.example.seapedia.domain.entities.ProductEntity
import com.example.seapedia.domain.repositories.ProductRepository
import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllSellerProductUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend fun run(): Flow<CommonState<List<ProductEntity>>>{
        return productRepository.getAllSellerProduct()
    }
}