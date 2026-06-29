package com.example.seapedia.domain.usecases.user

import com.example.seapedia.domain.repositories.UserRepository
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.UserRole
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddUserRoleUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun run(role: UserRole) : Flow<CommonState<String>>{
        return userRepository.addRole(role)
    }
}