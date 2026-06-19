package com.example.seapedia.domain.repositories

import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email:String, password:String): Flow<CommonState<String>>
}