package com.example.seapedia.domain.usecases.auth

import com.example.seapedia.domain.repositories.AuthRepository
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.UserRole
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend fun run (email: String, password: String,role: UserRole): Flow<CommonState<String>> {
        return authRepository.login(email,password,role)
    }
}