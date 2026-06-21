package com.example.seapedia.mapper

import com.example.seapedia.data.remote.responses.BaseResponse
import com.example.seapedia.data.remote.responses.ReviewResponse
import com.example.seapedia.domain.entities.ReviewEntity
import com.example.seapedia.global.utils.Mapper
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

class ReviewMapper : Mapper<BaseResponse<ReviewResponse>, ReviewEntity> {
    override fun mapFromResponse(type: BaseResponse<ReviewResponse>): ReviewEntity {
        return ReviewRawMapper().mapFromResponse(type.data)
    }
}
class ReviewRawMapper : Mapper<ReviewResponse, ReviewEntity> {
    @OptIn(ExperimentalTime::class)
    override fun mapFromResponse(type: ReviewResponse): ReviewEntity {
        return ReviewEntity(
            id = type.id,
            reviewerName = type.reviewerName,
            comment = type.comment,
            rating = type.rating,
            createdAt = Instant.parse(type.createdAt)
        )
    }
}