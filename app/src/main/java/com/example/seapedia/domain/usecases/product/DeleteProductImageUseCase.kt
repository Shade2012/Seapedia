package com.example.seapedia.domain.usecases.product

import com.example.seapedia.data.remote.body.ProductImageBody
import com.example.seapedia.domain.repositories.ProductRepository
import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteProductImageUseCase @Inject constructor(
    private val productRepository: ProductRepository
){
    suspend fun run(body: ProductImageBody): Flow<CommonState<String>>{
        return productRepository.deleteProductImages(body)
    }
}