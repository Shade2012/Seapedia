package com.example.seapedia.domain.usecases.job

import com.example.seapedia.domain.repositories.JobRepository
import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TakeJobUseCase @Inject constructor(
    private val jobRepository: JobRepository
) {
    suspend fun run(id: Int) : Flow<CommonState<String>>{
        return jobRepository.takeJob(id)
    }
}