package com.example.seapedia.domain.repositories

import com.example.seapedia.domain.entities.UserProfileEntity
import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.flow.Flow


interface UserRepository {
    suspend fun getProfile() : Flow<CommonState<UserProfileEntity>>
}