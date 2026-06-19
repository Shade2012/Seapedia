package com.example.seapedia.domain.repositories

import com.example.seapedia.data.remote.body.RegisterBody
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.UserRole
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email:String, password:String,role: UserRole): Flow<CommonState<String>>
    suspend fun register(registerBody: RegisterBody): Flow<CommonState<String>>
    fun getRole(): Flow<String?>
    fun getAccessToken(): Flow<String?>
    suspend fun setRole(role: UserRole): Unit
    suspend fun setAccessToken(accessToken: String): Unit
    suspend fun logout(): Boolean
}
