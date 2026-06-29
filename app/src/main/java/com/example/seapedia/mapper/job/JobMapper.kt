package com.example.seapedia.mapper.job

import com.example.seapedia.data.remote.responses.BaseResponse
import com.example.seapedia.data.remote.responses.JobResponse
import com.example.seapedia.data.remote.responses.job.JobDriverResponse

import com.example.seapedia.domain.entities.Driver
import com.example.seapedia.domain.entities.Job
import com.example.seapedia.global.utils.Mapper
import com.example.seapedia.mapper.order.OrderRawMapper
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

class JobListMapper: Mapper<BaseResponse<List<JobDriverResponse>>, List<Job>>{
    @OptIn(ExperimentalTime::class)
    override fun mapFromResponse(type: BaseResponse<List<JobDriverResponse>>): List<Job> {
        val data = type.data
        val mapper = JobRawMapper()
        return data.map { 
            mapper.mapFromResponse(it)
        }
    }
}
class JobRawMapper: Mapper<JobDriverResponse, Job>{
    @OptIn(ExperimentalTime::class)
    override fun mapFromResponse(type: JobDriverResponse): Job {
        val data = type
        return Job(
            id = data.id,
            earning = data.earning,
            createdAt = Instant.parse(data.createdAt),
            expiredDate = Instant.parse(data.expiredDate),
            isDone = data.isDone,
            order = OrderRawMapper().mapFromResponse(data.order)
        )
    }
}
class JobMapper: Mapper<BaseResponse<JobDriverResponse>, Job>{
    @OptIn(ExperimentalTime::class)
    override fun mapFromResponse(type: BaseResponse<JobDriverResponse>): Job {
        val mapper = JobRawMapper()
        val data = type.data
        return mapper.mapFromResponse(data)
    }
}
class JobMapperRawToDriver : Mapper<JobResponse, Driver?> {
    override fun mapFromResponse(type: JobResponse): Driver? {
        return type.driver?.let {
             Driver(
                fullName = type.driver.user.fullName
            )
        }
    }
}