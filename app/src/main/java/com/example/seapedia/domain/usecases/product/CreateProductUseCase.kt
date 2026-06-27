package com.example.seapedia.domain.usecases.product

import android.net.Uri
import com.example.seapedia.data.remote.body.CreateProductBody
import com.example.seapedia.data.remote.query.AllProductQuery
import com.example.seapedia.domain.entities.ProductEntity
import com.example.seapedia.domain.repositories.ProductRepository
import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateProductUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend fun run(product: CreateProductBody, images: List<Uri>): Flow<CommonState<String>>{
        return productRepository.createProduct(product,images)
    }
}