package com.example.seapedia.mapper

import com.example.seapedia.data.remote.responses.RegionResponse
import com.example.seapedia.domain.entities.Region
import com.example.seapedia.global.utils.Mapper
import kotlin.time.ExperimentalTime


class RegionMapper : Mapper<RegionResponse, Region> {
    @OptIn(ExperimentalTime::class)
    override fun mapFromResponse(type: RegionResponse): Region {
        return Region(
            id = type.id.toString(),
            name = type.name
        )
    }
}