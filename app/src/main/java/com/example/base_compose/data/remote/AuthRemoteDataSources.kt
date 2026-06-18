package com.example.base_compose.data.remote

import com.example.base_compose.data.remote.body.LoginBody
import com.example.base_compose.data.remote.responses.LoginResponse
import com.example.base_compose.data.remote.services.AuthService
import javax.inject.Inject

class AuthRemoteDataSources @Inject constructor(
    private val authService: AuthService,
) {
    suspend fun login(body: LoginBody): LoginResponse = authService.login(body)
}