package com.example.seapedia.data.remote.services

import com.example.seapedia.data.remote.body.AddressBody
import com.example.seapedia.data.remote.body.OrderStatusBody
import com.example.seapedia.data.remote.responses.AddressResponse
import com.example.seapedia.data.remote.responses.BaseMessage
import com.example.seapedia.data.remote.responses.BaseResponse
import com.example.seapedia.data.remote.responses.order.OrderAddressResponse
import com.example.seapedia.data.remote.responses.order.OrderHistoryResponse
import com.example.seapedia.data.remote.responses.order.OrderResponse
import com.example.seapedia.global.networks.NetworkConstant
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface AddressService {
    @GET(NetworkConstant.ADDRESS)
    suspend fun getAllAddress(): BaseResponse<List<AddressResponse>>

    @GET("${NetworkConstant.ADDRESS}/{id}")
    suspend fun getDetailAddress(
        @Path("id") id: Int
    ): BaseResponse<AddressResponse>

    @DELETE("${NetworkConstant.ADDRESS}/{id}")
    suspend fun deleteAddress(@Path("id") id: Int,) : BaseMessage

    @PATCH("${NetworkConstant.ADDRESS}/{id}")
    suspend fun updateAddress(
        @Path("id") id: Int,
        @Body body: AddressBody
    ): BaseMessage

    @POST(NetworkConstant.ADDRESS)
    suspend fun createAddress(
        @Body body: AddressBody
    ): BaseMessage
}