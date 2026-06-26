package com.example.seapedia.data.remote.services

import com.example.seapedia.data.remote.responses.BaseResponse
import com.example.seapedia.data.remote.responses.BaseResponseNullable
import com.example.seapedia.data.remote.responses.ReviewResponse
import com.example.seapedia.data.remote.responses.StoreResponse
import com.example.seapedia.global.networks.NetworkConstant
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.QueryMap

interface StoreService {
    @GET("${NetworkConstant.SELLERS}/store-check")
    suspend fun getValidStore(): BaseResponse<Boolean>
    @GET("${NetworkConstant.SELLERS}/store")
    suspend fun getStoreBySeller(): BaseResponseNullable<StoreResponse?>

    @Multipart
    @POST(NetworkConstant.STORES)
    suspend fun createStore(
        @Part image: MultipartBody.Part,
        @PartMap fields: Map<String, @JvmSuppressWildcards RequestBody>
    ): BaseResponse<StoreResponse>

    @Multipart
    @PATCH(NetworkConstant.STORES)
    suspend fun updateStore(
        @Part image: MultipartBody.Part?,
        @PartMap fields: Map<String, @JvmSuppressWildcards RequestBody>
    ): BaseResponse<StoreResponse>

}