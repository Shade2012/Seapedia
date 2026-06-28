package com.example.seapedia.domain.usecases.auth

import com.example.seapedia.domain.repositories.AuthRepository
import com.example.seapedia.global.utils.UserRole
import javax.inject.Inject


class SetUserIdUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun run(id: Int): Unit {
        return authRepository.setUserId(id)
    }
}