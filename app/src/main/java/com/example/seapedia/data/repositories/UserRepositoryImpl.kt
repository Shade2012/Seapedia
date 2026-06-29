package com.example.seapedia.data.repositories

import com.example.seapedia.data.remote.body.AddRoleBody
import com.example.seapedia.data.remote.sources.UserRemoteDataSources
import com.example.seapedia.domain.entities.BuyerProfile
import com.example.seapedia.domain.entities.UserProfile
import com.example.seapedia.domain.repositories.UserRepository
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.UserRole
import com.example.seapedia.global.utils.getErrorMessage
import com.example.seapedia.mapper.UserProfileMapper
import com.example.seapedia.mapper.user.BuyerMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSources: UserRemoteDataSources
) : UserRepository {
    override suspend fun getProfile(
    ): Flow<CommonState<UserProfile>> = flow{
        emit(CommonState.Loading())
        try {
            val response = userRemoteDataSources.getProfile()
            val entity = UserProfileMapper().mapFromResponse(response)
            emit(CommonState.Success<UserProfile>(data = entity))
        } catch (e: HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        } catch (e: Exception) {
            emit(CommonState.Error(message = e.message.toString()))
        }
    }

    override suspend fun getBuyerProfile(): Flow<CommonState<BuyerProfile>> = flow{
        emit(CommonState.Loading())
        try {
            val response = userRemoteDataSources.getProfile()
            val entity = BuyerMapper().mapFromResponse(response)
            emit(CommonState.Success<BuyerProfile>(data = entity))
        } catch (e: HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        } catch (e: Exception) {
            emit(CommonState.Error(message = e.message.toString()))
        }
    }

    override suspend fun addRole(userRole: UserRole): Flow<CommonState<String>> =flow{
        emit(CommonState.Loading())
        try {
            val response = userRemoteDataSources.addRole(
                AddRoleBody(
                    role = userRole
                )
            )
            emit(CommonState.Success<String>(data = response.message))
        } catch (e: HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        } catch (e: Exception) {
            emit(CommonState.Error(message = e.message.toString()))
        }
    }
}