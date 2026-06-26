package com.example.seapedia.data.remote.services

import com.example.seapedia.data.remote.responses.BaseResponse
import com.example.seapedia.data.remote.responses.RegionResponse
import com.example.seapedia.data.remote.responses.product.ProductResponse
import com.example.seapedia.global.networks.NetworkConstant
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface RegionService {
    @GET(NetworkConstant.PROVINCE)
    suspend fun getAllProvince(): BaseResponse<List<RegionResponse>>

    @GET("${NetworkConstant.CITY}/{id}")
    suspend fun getAllCity(@Path("id") id: String): BaseResponse<List<RegionResponse>>

    @GET("${NetworkConstant.DISTRICT}/{id}")
    suspend fun getAllDistrict(@Path("id") id: String): BaseResponse<List<RegionResponse>>

    @GET("${NetworkConstant.VILLAGE}/{id}")
    suspend fun getAllVillages(@Path("id") id: String): BaseResponse<List<RegionResponse>>
}