package com.example.seapedia.data.remote.services

import com.example.seapedia.data.remote.body.BuyerPhoneNumberBody
import com.example.seapedia.data.remote.responses.BaseMessage
import com.example.seapedia.data.remote.responses.BaseResponse
import com.example.seapedia.global.networks.NetworkConstant
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH

interface BuyerService {
    @GET("${NetworkConstant.BUYER}/check")
    suspend fun checkValidBuyer() : BaseResponse<Boolean>

    @PATCH(NetworkConstant.BUYER)
    suspend fun updatePhoneNumber(
        @Body body: BuyerPhoneNumberBody
    ) : BaseMessage
}