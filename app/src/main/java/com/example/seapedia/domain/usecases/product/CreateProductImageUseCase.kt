package com.example.seapedia.domain.usecases.product

import android.net.Uri
import com.example.seapedia.data.remote.query.ProductImagesQuery
import com.example.seapedia.domain.entities.ProductImageEntity
import com.example.seapedia.domain.repositories.ProductRepository
import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateProductImageUseCase @Inject constructor(
    private val productRepository: ProductRepository
){
    suspend fun run(id: Int, images: List<Uri>): Flow<CommonState<String>>{
        return productRepository.addProductImages(id,images)
    }
}