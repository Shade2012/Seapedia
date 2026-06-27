package com.example.seapedia.domain.usecases.product

import com.example.seapedia.domain.repositories.ProductRepository
import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteProductUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend fun run(id: Int) : Flow<CommonState<String>>{
        return productRepository.deleteProduct(id)
    }
}