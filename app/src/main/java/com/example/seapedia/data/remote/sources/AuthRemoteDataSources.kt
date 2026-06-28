package com.example.seapedia.data.remote.sources

import com.example.seapedia.data.remote.body.LoginBody
import com.example.seapedia.data.remote.body.RegisterBody
import com.example.seapedia.data.remote.responses.LoginResponse
import com.example.seapedia.data.remote.responses.RegisterResponse
import com.example.seapedia.data.remote.services.AuthService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRemoteDataSources @Inject constructor(
    private val authService: AuthService,
) {
    suspend fun login(body: LoginBody): LoginResponse = authService.login(body)
    suspend fun register(body: RegisterBody): RegisterResponse = authService.register(body)
}