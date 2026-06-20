package com.example.seapedia.data.remote.services

import com.example.seapedia.data.remote.responses.BaseResponse
import com.example.seapedia.data.remote.responses.ProfileResponse
import com.example.seapedia.global.networks.NetworkConstant
import retrofit2.http.GET

interface UserService {
    @GET("${NetworkConstant.USERS}/profile")
    suspend fun getProfile(): BaseResponse<ProfileResponse>
}