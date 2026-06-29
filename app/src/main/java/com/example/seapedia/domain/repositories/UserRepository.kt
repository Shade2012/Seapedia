package com.example.seapedia.domain.repositories

import com.example.seapedia.domain.entities.BuyerProfile
import com.example.seapedia.domain.entities.UserProfile
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.UserRole
import kotlinx.coroutines.flow.Flow


interface UserRepository {
    suspend fun getProfile() : Flow<CommonState<UserProfile>>
    suspend fun getBuyerProfile() : Flow<CommonState<BuyerProfile>>
    suspend fun addRole(userRole: UserRole) : Flow<CommonState<String>>
}