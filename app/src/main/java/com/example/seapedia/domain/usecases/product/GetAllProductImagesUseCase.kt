package com.example.seapedia.domain.usecases.product

import com.example.seapedia.data.remote.query.ProductImagesQuery
import com.example.seapedia.domain.entities.ProductImageEntity
import com.example.seapedia.domain.repositories.ProductRepository
import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllProductImagesUseCase @Inject constructor(
    private val productRepository: ProductRepository
){
    suspend fun run(queries: ProductImagesQuery): Flow<CommonState<List<ProductImageEntity>>>{
        return productRepository.getAllProductImages(queries)
    }
}