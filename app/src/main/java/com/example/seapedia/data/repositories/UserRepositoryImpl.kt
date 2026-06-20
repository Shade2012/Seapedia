package com.example.seapedia.data.repositories

import com.example.seapedia.data.remote.UserRemoteDataSources
import com.example.seapedia.domain.entities.UserProfileEntity
import com.example.seapedia.domain.repositories.UserRepository
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.getErrorMessage
import com.example.seapedia.mapper.UserProfileMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSources: UserRemoteDataSources
) : UserRepository {
    override suspend fun getProfile(
    ): Flow<CommonState<UserProfileEntity>> = flow{
        emit(CommonState.Loading())
        try {
            val response = userRemoteDataSources.getProfile()
            val entity = UserProfileMapper().mapFromResponse(response)
            emit(CommonState.Success<UserProfileEntity>(data = entity))
        } catch (e: HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        } catch (e: Exception) {
            emit(CommonState.Error(message = e.message.toString()))
        }
    }
}