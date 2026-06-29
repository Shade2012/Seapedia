package com.example.seapedia.domain.repositories

import com.example.seapedia.domain.entities.Job
import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.flow.Flow

interface JobRepository {
    suspend fun getListAvailableJob(): Flow<CommonState<List<Job>>>
    suspend fun getListCurrentJob(): Flow<CommonState<List<Job>>>
    suspend fun getListHistoryJob(): Flow<CommonState<List<Job>>>
    suspend fun getJob(id: Int): Flow<CommonState<Job>>
    suspend fun confirmJob(id: Int): Flow<CommonState<String>>
    suspend fun takeJob(id: Int): Flow<CommonState<String>>
}