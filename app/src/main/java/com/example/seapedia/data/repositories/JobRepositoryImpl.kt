package com.example.seapedia.data.repositories

import com.example.seapedia.data.remote.body.job.JobBody
import com.example.seapedia.data.remote.sources.JobRemoteDataSources
import com.example.seapedia.domain.entities.Job
import com.example.seapedia.domain.repositories.JobRepository
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.getErrorMessage
import com.example.seapedia.mapper.job.JobListMapper
import com.example.seapedia.mapper.job.JobMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class JobRepositoryImpl @Inject constructor(
    private val jobRemoteDataSources: JobRemoteDataSources
): JobRepository {
    override suspend fun getListAvailableJob(): Flow<CommonState<List<Job>>> = flow{
        try {
            val response = jobRemoteDataSources.getAvailableJob()
            val data = JobListMapper().mapFromResponse(response)
            emit(CommonState.Success<List<Job>>(data = data))
        } catch (e: retrofit2.HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        } catch (e: Exception) {
            emit(CommonState.Error(message = e.message.toString()))
        }
    }

    override suspend fun getListCurrentJob(): Flow<CommonState<List<Job>>> = flow{
        try {
            val response = jobRemoteDataSources.getCurrentJob()
            val data = JobListMapper().mapFromResponse(response)
            emit(CommonState.Success<List<Job>>(data = data))
        } catch (e: retrofit2.HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        } catch (e: Exception) {
            emit(CommonState.Error(message = e.message.toString()))
        }
    }

    override suspend fun getListHistoryJob(): Flow<CommonState<List<Job>>> = flow{
        try {
            val response = jobRemoteDataSources.getHistoryJob()
            val data = JobListMapper().mapFromResponse(response)
            emit(CommonState.Success<List<Job>>(data = data))
        } catch (e: retrofit2.HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        } catch (e: Exception) {
            emit(CommonState.Error(message = e.message.toString()))
        }
    }

    override suspend fun getJob(id: Int): Flow<CommonState<Job>> = flow{
        try {
            val response = jobRemoteDataSources.getDetailJob(id)
            val data = JobMapper().mapFromResponse(response)
            emit(CommonState.Success<Job>(data = data))
        } catch (e: retrofit2.HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        } catch (e: Exception) {
            emit(CommonState.Error(message = e.message.toString()))
        }
    }

    override suspend fun confirmJob(id: Int): Flow<CommonState<String>> = flow{
        try {
            val response = jobRemoteDataSources.confirmJob(id)
            emit(CommonState.Success<String>(data = response.message))
        } catch (e: retrofit2.HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        } catch (e: Exception) {
            emit(CommonState.Error(message = e.message.toString()))
        }
    }

    override suspend fun takeJob(id: Int): Flow<CommonState<String>> = flow{
        try {
            val response = jobRemoteDataSources.takeJob(JobBody(
                jobId = id
            ))
            emit(CommonState.Success<String>(data = response.message))
        } catch (e: retrofit2.HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        } catch (e: Exception) {
            emit(CommonState.Error(message = e.message.toString()))
        }
    }
}