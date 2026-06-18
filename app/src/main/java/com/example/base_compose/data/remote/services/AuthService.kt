package com.example.base_compose.data.remote.services

import com.example.base_compose.data.remote.body.LoginBody
import com.example.base_compose.data.remote.responses.LoginResponse
import com.example.base_compose.global.networks.NetworkConstant
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("${NetworkConstant.USERS}/login")
    suspend fun login(@Body body: LoginBody): LoginResponse
}