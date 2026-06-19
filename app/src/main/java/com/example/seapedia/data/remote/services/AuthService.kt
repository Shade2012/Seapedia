package com.example.seapedia.data.remote.services

import com.example.seapedia.data.remote.body.LoginBody
import com.example.seapedia.data.remote.body.RegisterBody
import com.example.seapedia.data.remote.responses.LoginResponse
import com.example.seapedia.data.remote.responses.RegisterResponse
import com.example.seapedia.global.networks.NetworkConstant
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("${NetworkConstant.USERS}/login")
    suspend fun login(@Body body: LoginBody): LoginResponse

    @POST("${NetworkConstant.USERS}/register")
    suspend fun register(@Body body: RegisterBody): RegisterResponse
}