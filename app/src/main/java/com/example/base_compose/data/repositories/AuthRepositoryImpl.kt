package com.example.base_compose.data.repositories

import android.util.Log
import com.example.base_compose.data.remote.AuthRemoteDataSources
import com.example.base_compose.data.remote.body.LoginBody
import com.example.base_compose.domain.repositories.AuthRepository
import com.example.base_compose.global.utils.CommonState
import com.example.base_compose.global.utils.getErrorMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSources,
) : AuthRepository {
    override suspend fun login(
        email: String,
        password: String
    ): Flow<CommonState<String>> = flow{
        emit(CommonState.Loading())
        try {
            val response = authRemoteDataSource.login(LoginBody(email, password))
            emit(CommonState.Success<String>(data = response.token))
        } catch (e: HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        } catch (e: Exception) {
            emit(CommonState.Error(message = e.message.toString()))
        }
    }
}