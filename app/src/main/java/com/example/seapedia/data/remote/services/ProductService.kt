package com.example.seapedia.data.remote.services

import com.example.seapedia.data.remote.body.LoginBody
import com.example.seapedia.data.remote.query.AllProductQuery
import com.example.seapedia.data.remote.responses.BaseResponse
import com.example.seapedia.data.remote.responses.LoginResponse
import com.example.seapedia.data.remote.responses.product.ProductResponse
import com.example.seapedia.global.networks.NetworkConstant
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ProductService {
    @GET(NetworkConstant.PRODUCTS)
    suspend fun getAllProduct(@QueryMap queries: Map<String, String>): BaseResponse<List<ProductResponse>>

    @GET("${NetworkConstant.PRODUCTS}/{id}")
    suspend fun getDetailProduct(@Path("id") id: Int): BaseResponse<List<ProductResponse>>

    @GET("${NetworkConstant.SELLERS}/${NetworkConstant.PRODUCTS}")
    suspend fun getAllProductSeller(): BaseResponse<List<ProductResponse>>

    @Multipart
    @POST(NetworkConstant.PRODUCTS)
    suspend fun createProduct(
        @Part("name") name: String,
        @Part("price") price: Int,
        @Part("stock") stock: Int,
        @Part("is_available") isAvailable: Boolean,
        @Part("types") types: String,
        @Part images: List<MultipartBody.Part>
    ) : BaseResponse<List<ProductResponse>>
}