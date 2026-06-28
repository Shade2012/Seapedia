package com.example.seapedia.mapper.job

import com.example.seapedia.data.remote.responses.JobResponse
import com.example.seapedia.domain.entities.Driver
import com.example.seapedia.global.utils.Mapper

class JobMapperRawToDriver : Mapper<JobResponse, Driver?> {
    override fun mapFromResponse(type: JobResponse): Driver? {
        return type.driver?.let {
             Driver(
                fullName = type.driver.user.fullName
            )
        }
    }
}