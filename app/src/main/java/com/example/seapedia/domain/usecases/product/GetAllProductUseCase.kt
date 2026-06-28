package com.example.seapedia.domain.usecases.product

import com.example.seapedia.data.remote.query.AllProductQuery
import com.example.seapedia.domain.entities.Product
import com.example.seapedia.domain.repositories.ProductRepository
import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllProductUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend fun run(queries: AllProductQuery): Flow<CommonState<List<Product>>>{
        return productRepository.getAllProduct(queries)
    }
}