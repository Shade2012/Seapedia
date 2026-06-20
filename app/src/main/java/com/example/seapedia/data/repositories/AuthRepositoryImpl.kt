package com.example.seapedia.data.repositories

import android.util.Log
import com.example.seapedia.data.local.AuthLocalDataSources
import com.example.seapedia.data.remote.AuthRemoteDataSources
import com.example.seapedia.data.remote.body.LoginBody
import com.example.seapedia.data.remote.body.RegisterBody
import com.example.seapedia.domain.repositories.AuthRepository
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.UserRole
import com.example.seapedia.global.utils.getErrorMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSources,
    private val authLocalDataSource: AuthLocalDataSources,
) : AuthRepository {
    override suspend fun login(
        email: String,
        password: String,
        role: UserRole
    ): Flow<CommonState<String>> = flow{
        emit(CommonState.Loading())
        try {
            val response = authRemoteDataSource.login(LoginBody(email, password,role.name))
            emit(CommonState.Success<String>(data = response.data))
        } catch (e: HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        } catch (e: Exception) {
            emit(CommonState.Error(message = e.message.toString()))
        }
    }

    override suspend fun register(
        registerBody: RegisterBody
    ): Flow<CommonState<String>> = flow{
        emit(CommonState.Loading())
        try {
            val response = authRemoteDataSource.register(registerBody)
            emit(CommonState.Success<String>(data = response.message))
        } catch (e: HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
            Log.d("AuthImpl Http",message)
        } catch (e: Exception) {
            Log.d("AuthImpl Excep",e.message.toString())
            emit(CommonState.Error(message = e.message.toString()))
        }
    }


    override fun getRole(): Flow<String?>{
        return authLocalDataSource.getRole()
    }

    override fun getAccessToken(): Flow<String?> {
        return authLocalDataSource.getAccessToken()
    }

    override fun getEmail(): Flow<String?> {
        return authLocalDataSource.getEmail()
    }

    override fun getPassword(): Flow<String?> {
        return authLocalDataSource.getPassword()
    }

    override suspend fun setRole(role: UserRole) {
        return authLocalDataSource.setRole(role)
    }

    override suspend fun setAccessToken(accessToken: String) {
        return authLocalDataSource.setAccessToken(accessToken)
    }

    override suspend fun setEmail(email: String) {
        return authLocalDataSource.setEmail(email)
    }

    override suspend fun setPassword(password: String) {
        return authLocalDataSource.setPassword(password)
    }

    override suspend fun logout(): Boolean {
        return authLocalDataSource.logout()
    }
}