package com.example.seapedia.domain.usecases.job

import com.example.seapedia.domain.entities.Job
import com.example.seapedia.domain.repositories.JobRepository
import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetListCurrentJobUseCase @Inject constructor(
    private val jobRepository: JobRepository
) {
    suspend fun run() : Flow<CommonState<List<Job>>>{
        return jobRepository.getListCurrentJob()
    }
}