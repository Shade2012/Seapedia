package com.example.seapedia.domain.usecases.auth

import com.example.seapedia.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetUserIdUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    fun run(): Flow<Int?> {
        return authRepository.getUserId()
    }
}