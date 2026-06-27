package com.example.seapedia.domain.repositories

import android.net.Uri
import com.example.seapedia.data.remote.body.CreateProductBody
import com.example.seapedia.data.remote.body.ProductImageBody
import com.example.seapedia.data.remote.query.AllProductQuery
import com.example.seapedia.data.remote.query.AllProductSellerQuery
import com.example.seapedia.data.remote.query.ProductImagesQuery
import com.example.seapedia.domain.entities.ProductEntity
import com.example.seapedia.domain.entities.ProductImageEntity
import com.example.seapedia.domain.entities.SellerProductEntity
import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getAllProduct(queries: AllProductQuery): Flow<CommonState<List<ProductEntity>>>
    suspend fun getDetailProduct(id: Int): Flow<CommonState<ProductEntity>>
    suspend fun getAllSellerProduct(queries: AllProductSellerQuery): Flow<CommonState<SellerProductEntity>>
    suspend fun updateProduct(id:Int, product: CreateProductBody) : Flow<CommonState<String>>
    suspend fun createProduct(product: CreateProductBody, images: List<Uri>) : Flow<CommonState<String>>

    suspend fun getAllProductImages(queries: ProductImagesQuery): Flow<CommonState<List<ProductImageEntity>>>
    suspend fun addProductImages(id:Int, images: List<Uri>) : Flow<CommonState<String>>
    suspend fun deleteProductImages(product: ProductImageBody) : Flow<CommonState<String>>
}
