package com.example.seapedia.domain.usecases.product

import com.example.seapedia.data.remote.body.CreateProductBody
import com.example.seapedia.domain.entities.ProductEntity
import com.example.seapedia.domain.repositories.ProductRepository
import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateProductUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend fun run(id: Int,product: CreateProductBody): Flow<CommonState<String>>{
        return productRepository.updateProduct(id,product)
    }
}