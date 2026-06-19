package com.example.seapedia.data.remote

import com.example.seapedia.data.remote.body.LoginBody
import com.example.seapedia.data.remote.responses.LoginResponse
import com.example.seapedia.data.remote.services.AuthService
import javax.inject.Inject

class AuthRemoteDataSources @Inject constructor(
    private val authService: AuthService,
) {
    suspend fun login(body: LoginBody): LoginResponse = authService.login(body)
}