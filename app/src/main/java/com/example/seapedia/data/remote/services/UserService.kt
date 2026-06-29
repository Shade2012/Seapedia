package com.example.seapedia.data.remote.services

import com.example.seapedia.data.remote.body.AddRoleBody
import com.example.seapedia.data.remote.responses.BaseMessage
import com.example.seapedia.data.remote.responses.BaseResponse
import com.example.seapedia.data.remote.responses.ProfileResponse
import com.example.seapedia.global.networks.NetworkConstant
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {
    @GET("${NetworkConstant.USERS}/profile")
    suspend fun getProfile(): BaseResponse<ProfileResponse>

    @POST("${NetworkConstant.USERS}/roles")
    suspend fun addRole(
        @Body body: AddRoleBody
    ): BaseMessage
}