package com.example.seapedia.data.remote.services

import com.example.seapedia.data.remote.body.LoginBody
import com.example.seapedia.data.remote.query.AllProductQuery
import com.example.seapedia.data.remote.responses.BaseResponse
import com.example.seapedia.data.remote.responses.LoginResponse
import com.example.seapedia.data.remote.responses.product.ProductResponse
import com.example.seapedia.global.networks.NetworkConstant
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ProductService {
    @GET(NetworkConstant.PRODUCTS)
    suspend fun getAllProduct(@QueryMap queries: Map<String, String>): BaseResponse<List<ProductResponse>>

    @GET("${NetworkConstant.PRODUCTS}/{id}")
    suspend fun getDetailProduct(@Path("id") id: Int): BaseResponse<List<ProductResponse>>
}