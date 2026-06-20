package com.example.seapedia.domain.usecases.user

import com.example.seapedia.domain.entities.UserProfileEntity
import com.example.seapedia.domain.repositories.UserRepository
import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun run() : Flow<CommonState<UserProfileEntity>>{
        return userRepository.getProfile()
    }
}