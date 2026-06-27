package com.example.seapedia.data.remote.services

import com.example.seapedia.data.remote.body.CreateProductBody
import com.example.seapedia.data.remote.body.ProductImageBody
import com.example.seapedia.data.remote.responses.BaseMessage
import com.example.seapedia.data.remote.responses.BaseResponse
import com.example.seapedia.data.remote.responses.product.ProductImageResponse
import com.example.seapedia.data.remote.responses.product.ProductResponse
import com.example.seapedia.data.remote.responses.product.SellerProductsResponse
import com.example.seapedia.global.networks.NetworkConstant
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ProductService {
    @GET(NetworkConstant.PRODUCTS)
    suspend fun getAllProduct(@QueryMap queries: Map<String, String>): BaseResponse<List<ProductResponse>>

    @GET("${NetworkConstant.PRODUCTS}/{id}")
    suspend fun getDetailProduct(@Path("id") id: Int): BaseResponse<ProductResponse>

    @Multipart
    @POST(NetworkConstant.PRODUCTS)
    suspend fun createProduct(
        @Part images: List<MultipartBody.Part>,
        @PartMap fields: Map<String, @JvmSuppressWildcards RequestBody>,
    ) : BaseMessage

    @PATCH("${NetworkConstant.PRODUCTS}/{id}")
    suspend fun updateProduct(
        @Path("id") id: Int,
        @Body body: CreateProductBody
    ): BaseMessage

    @GET("${NetworkConstant.SELLERS}/${NetworkConstant.PRODUCTS}")
    suspend fun getAllProductBySeller(@QueryMap queries: Map<String, String>): BaseResponse<SellerProductsResponse>

    @GET(NetworkConstant.PRODUCT_IMAGES)
    suspend fun getProductImages(
        @QueryMap queries: Map<String, String>
    ): BaseResponse<List<ProductImageResponse>>

    @Multipart
    @POST("${NetworkConstant.PRODUCT_IMAGES}/{id}")
    suspend fun addProductImages(
        @Path("id") id: Int,
        @Part images: List<MultipartBody.Part>)
    : BaseResponse<List<ProductImageResponse>>

    @HTTP(
        method = "DELETE",
        path = NetworkConstant.PRODUCT_IMAGES,
        hasBody = true
    )
    suspend fun deleteProductImages(@Body body: ProductImageBody) : BaseResponse<List<ProductImageResponse>>
}