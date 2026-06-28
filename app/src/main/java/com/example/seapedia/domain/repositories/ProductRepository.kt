package com.example.seapedia.domain.repositories

import android.net.Uri
import com.example.seapedia.data.remote.body.CreateProductBody
import com.example.seapedia.data.remote.body.ProductImageBody
import com.example.seapedia.data.remote.query.AllProductQuery
import com.example.seapedia.data.remote.query.AllProductSellerQuery
import com.example.seapedia.data.remote.query.ProductImagesQuery
import com.example.seapedia.domain.entities.Product
import com.example.seapedia.domain.entities.ProductImage
import com.example.seapedia.domain.entities.SellerProduct
import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getAllProduct(queries: AllProductQuery): Flow<CommonState<List<Product>>>
    suspend fun getDetailProduct(id: Int): Flow<CommonState<Product>>
    suspend fun getAllSellerProduct(queries: AllProductSellerQuery): Flow<CommonState<SellerProduct>>
    suspend fun updateProduct(id:Int, product: CreateProductBody) : Flow<CommonState<String>>
    suspend fun createProduct(product: CreateProductBody, images: List<Uri>) : Flow<CommonState<String>>

    suspend fun getAllProductImages(queries: ProductImagesQuery): Flow<CommonState<List<ProductImage>>>
    suspend fun addProductImages(id:Int, images: List<Uri>) : Flow<CommonState<String>>
    suspend fun deleteProductImages(product: ProductImageBody) : Flow<CommonState<String>>

    suspend fun deleteProduct(id:Int): Flow<CommonState<String>>
}
