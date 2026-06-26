package com.example.seapedia.data.remote

import com.example.seapedia.data.remote.body.LoginBody
import com.example.seapedia.data.remote.body.RegisterBody
import com.example.seapedia.data.remote.query.AllProductQuery
import com.example.seapedia.data.remote.query.toMap
import com.example.seapedia.data.remote.responses.BaseResponse
import com.example.seapedia.data.remote.responses.LoginResponse
import com.example.seapedia.data.remote.responses.RegisterResponse
import com.example.seapedia.data.remote.responses.product.ProductResponse
import com.example.seapedia.data.remote.services.ProductService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRemoteDataSources @Inject constructor(
    private val productService: ProductService,
) {
    suspend fun getAllProduct(queries: AllProductQuery): BaseResponse<List<ProductResponse>> = productService.getAllProduct(queries.toMap())
    suspend fun getAllProductSellers(): BaseResponse<List<ProductResponse>> = productService.getAllProductSeller()
    suspend fun getDetailProduct(id: Int): BaseResponse<List<ProductResponse>> = productService.getDetailProduct(id)
}