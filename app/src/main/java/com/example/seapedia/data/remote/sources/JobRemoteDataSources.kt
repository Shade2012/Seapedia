package com.example.seapedia.data.remote.sources

import com.example.seapedia.data.remote.body.job.JobBody
import com.example.seapedia.data.remote.responses.BaseMessage
import com.example.seapedia.data.remote.responses.BaseResponse
import com.example.seapedia.data.remote.responses.job.JobDriverResponse
import com.example.seapedia.data.remote.services.JobService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JobRemoteDataSources @Inject constructor(
    private val jobService: JobService
){
    suspend fun getAvailableJob(): BaseResponse<List<JobDriverResponse>> = jobService.getAvailableJob()
    suspend fun getHistoryJob(): BaseResponse<List<JobDriverResponse>> = jobService.getHistoryJob()
    suspend fun getCurrentJob(): BaseResponse<List<JobDriverResponse>> = jobService.getCurrentJob()
    suspend fun getDetailJob(id: Int): BaseResponse<JobDriverResponse> = jobService.getDetailJob(id)
    suspend fun takeJob(body: JobBody): BaseMessage = jobService.takeJob(body = body)
    suspend fun confirmJob(id: Int): BaseMessage = jobService.confirmJob(id)
}
