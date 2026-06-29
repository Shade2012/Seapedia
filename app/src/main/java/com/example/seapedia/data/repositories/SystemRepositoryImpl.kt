package com.example.seapedia.data.repositories

import com.example.seapedia.data.remote.sources.SystemRemoteDataSources
import com.example.seapedia.domain.entities.BuyerProfile
import com.example.seapedia.domain.repositories.SystemRepository
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.getErrorMessage
import com.example.seapedia.mapper.user.BuyerMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

class SystemRepositoryImpl @Inject constructor(
    private val systemRemoteDataSources: SystemRemoteDataSources
): SystemRepository{

    @OptIn(ExperimentalTime::class)
    override suspend fun getDay(): Flow<CommonState<Instant>> = flow{
        try {
            val response = systemRemoteDataSources.getDay()
            emit(CommonState.Success<Instant>(data = Instant.parse(response.data)))
        } catch (e: HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        } catch (e: CancellationException) {
            throw e
        }  catch (e: Exception) {
            emit(CommonState.Error(message = e.message.toString()))
        }
    }
}