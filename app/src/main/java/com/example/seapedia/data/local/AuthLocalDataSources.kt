package com.example.seapedia.data.local

import com.example.seapedia.data.local.user.UserDataStoreManager
import com.example.seapedia.global.utils.UserRole
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthLocalDataSources @Inject constructor(
    private val userDataStoreManager: UserDataStoreManager
) {
    fun getAccessToken(): Flow<String?> = userDataStoreManager.getAccessToken()
    fun getRole(): Flow<String?> = userDataStoreManager.getRole()
    fun getEmail(): Flow<String?> = userDataStoreManager.getEmail()
    fun getPassword(): Flow<String?> = userDataStoreManager.getPassword()
    fun getUserId(): Flow<Int?> = userDataStoreManager.getUserId()

    suspend fun setEmail(email: String): Unit = userDataStoreManager.setEmail(email)
    suspend fun setPassword(password: String): Unit = userDataStoreManager.setPassword(password)

    suspend fun setUserId(id: Int): Unit = userDataStoreManager.setUserId(id)
    suspend fun setAccessToken(accessToken: String): Unit = userDataStoreManager.setAccessToken(accessToken)
    suspend fun setRole(role: UserRole): Unit = userDataStoreManager.setRole(role)
    suspend fun logout(): Boolean = userDataStoreManager.logout()
}