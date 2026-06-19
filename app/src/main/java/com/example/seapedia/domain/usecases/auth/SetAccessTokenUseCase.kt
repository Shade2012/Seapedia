package com.example.seapedia.domain.usecases.auth

import com.example.seapedia.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SetAccessTokenUseCase  @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun run(accessToken: String): Unit {
        return authRepository.setAccessToken(accessToken)
    }
}