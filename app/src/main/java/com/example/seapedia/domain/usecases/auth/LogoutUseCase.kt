package com.example.seapedia.domain.usecases.auth

import com.example.seapedia.domain.repositories.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun run(): Boolean {
        return authRepository.logout()
    }
}