package com.example.base_compose.domain.repositories

import com.example.base_compose.global.utils.CommonState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email:String, password:String): Flow<CommonState<String>>
}