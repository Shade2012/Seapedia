package com.example.seapedia.data.remote.sources

import com.example.seapedia.data.remote.body.CreateProductBody
import com.example.seapedia.data.remote.body.ProductImageBody
import com.example.seapedia.data.remote.query.AllProductQuery
import com.example.seapedia.data.remote.query.AllProductSellerQuery
import com.example.seapedia.data.remote.query.ProductImagesQuery
import com.example.seapedia.data.remote.query.toMap
import com.example.seapedia.data.remote.responses.BaseMessage
import com.example.seapedia.data.remote.responses.BaseResponse
import com.example.seapedia.data.remote.responses.product.ProductImageResponse
import com.example.seapedia.data.remote.responses.product.ProductResponse
import com.example.seapedia.data.remote.responses.product.SellerProductsResponse
import com.example.seapedia.data.remote.services.ProductService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRemoteDataSources @Inject constructor(
    private val productService: ProductService,
) {
    suspend fun getAllProduct(queries: AllProductQuery): BaseResponse<List<ProductResponse>> = productService.getAllProduct(queries.toMap())
    suspend fun getAllProductBySeller(queries: AllProductSellerQuery): BaseResponse<SellerProductsResponse> = productService.getAllProductBySeller(queries.toMap())
    suspend fun getDetailProduct(id: Int): BaseResponse<ProductResponse> = productService.getDetailProduct(id)
    suspend fun createProductBySeller(productBody: CreateProductBody, images: List<MultipartBody.Part>): BaseMessage = productService.createProduct(
        images = images,
        fields = mapOf(
            "name" to productBody.name.toRequestBody(),
            "price" to productBody.price.toString().toRequestBody(),
            "stock" to productBody.stock.toString().toRequestBody(),
        ) + if (productBody.types.isNotEmpty()) {
            mapOf(
                "types" to Json.encodeToString(productBody.types)
                    .toRequestBody("application/json".toMediaType())
            )
        } else {
            emptyMap()
        }
    )
    suspend fun updateProductBySeller(id:Int,productBody: CreateProductBody): BaseMessage = productService.updateProduct(id,productBody)
    suspend fun getProductImages(queries: ProductImagesQuery): BaseResponse<List<ProductImageResponse>> = productService.getProductImages(queries = queries.toMap())
    suspend fun addProductImage(id:Int, images: List<MultipartBody.Part>): BaseResponse<List<ProductImageResponse>> = productService.addProductImages(id = id,images = images)
    suspend fun deleteProductImages(body: ProductImageBody): BaseResponse<List<ProductImageResponse>> = productService.deleteProductImages(body)
    suspend fun deleteProduct(id: Int): BaseMessage = productService.deleteProduct(id)
}