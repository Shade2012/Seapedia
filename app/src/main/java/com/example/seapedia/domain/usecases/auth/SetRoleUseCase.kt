package com.example.seapedia.domain.usecases.auth

import com.example.seapedia.domain.repositories.AuthRepository
import com.example.seapedia.global.utils.UserRole
import javax.inject.Inject

class SetRoleUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun run(role: UserRole): Unit {
        return authRepository.setRole(role)
    }
}