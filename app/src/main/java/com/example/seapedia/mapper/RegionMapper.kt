package com.example.seapedia.mapper

import com.example.seapedia.data.remote.responses.BaseResponse
import com.example.seapedia.data.remote.responses.RegionResponse
import com.example.seapedia.data.remote.responses.ReviewResponse
import com.example.seapedia.domain.entities.RegionEntity
import com.example.seapedia.domain.entities.ReviewEntity
import com.example.seapedia.global.utils.Mapper
import kotlin.time.ExperimentalTime
import kotlin.time.Instant


class RegionMapper : Mapper<RegionResponse, RegionEntity> {
    @OptIn(ExperimentalTime::class)
    override fun mapFromResponse(type: RegionResponse): RegionEntity {
        return RegionEntity(
            id = type.id.toString(),
            name = type.name
        )
    }
}