package com.example.seapedia.domain.usecases.auth

import com.example.seapedia.data.remote.body.RegisterBody
import com.example.seapedia.domain.repositories.AuthRepository
import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend fun run (registerBody: RegisterBody): Flow<CommonState<String>> {
        return authRepository.register(registerBody)
    }
}