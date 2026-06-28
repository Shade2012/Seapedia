package com.example.seapedia.data.remote.services

import com.example.seapedia.data.remote.responses.BaseResponse
import com.example.seapedia.global.networks.NetworkConstant
import retrofit2.http.GET

interface BuyerService {
    @GET("${NetworkConstant.BUYER}/check")
    fun checkValidBuyer() : BaseResponse<Boolean>

}