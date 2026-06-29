package com.example.seapedia.data.repositories

import com.example.seapedia.data.remote.body.BuyerPhoneNumberBody
import com.example.seapedia.data.remote.sources.BuyerRemoteDataSources
import com.example.seapedia.domain.repositories.BuyerRepository
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.getErrorMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BuyerRepositoryImpl @Inject constructor(
    private val buyerRemoteDataSources: BuyerRemoteDataSources
) : BuyerRepository {
    override suspend fun checkValidBuyer(): Flow<CommonState<Boolean>> = flow{
        try {
            val response = buyerRemoteDataSources.checkValidBuyer()
            emit(CommonState.Success<Boolean>(data = response.data))
        } catch (e: retrofit2.HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        } catch (e: Exception) {
            emit(CommonState.Error(message = e.message.toString()))
        }
    }

    override suspend fun updateBuyer(body: BuyerPhoneNumberBody): Flow<CommonState<String>> = flow{
        emit(CommonState.Loading())
        try {
            val response = buyerRemoteDataSources.updateBuyer(body)
            emit(CommonState.Success<String>(data = response.message))
        } catch (e: retrofit2.HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        } catch (e: Exception) {
            emit(CommonState.Error(message = e.message.toString()))
        }
    }
}