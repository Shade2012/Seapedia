package com.example.base_compose.domain.usecases.auth

import com.example.base_compose.domain.repositories.AuthRepository
import com.example.base_compose.global.utils.CommonState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend fun run (email: String, password: String): Flow<CommonState<String>> {
        return authRepository.login(email,password)
    }
}