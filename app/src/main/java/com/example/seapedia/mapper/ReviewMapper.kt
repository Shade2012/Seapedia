package com.example.seapedia.mapper

import com.example.seapedia.data.remote.responses.BaseResponse
import com.example.seapedia.data.remote.responses.ReviewResponse
import com.example.seapedia.domain.entities.Review
import com.example.seapedia.global.utils.Mapper
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

class ReviewMapper : Mapper<BaseResponse<ReviewResponse>, Review> {
    override fun mapFromResponse(type: BaseResponse<ReviewResponse>): Review {
        return ReviewRawMapper().mapFromResponse(type.data)
    }
}
class ReviewRawMapper : Mapper<ReviewResponse, Review> {
    @OptIn(ExperimentalTime::class)
    override fun mapFromResponse(type: ReviewResponse): Review {
        return Review(
            id = type.id,
            reviewerName = type.reviewerName,
            comment = type.comment,
            rating = type.rating,
            createdAt = Instant.parse(type.createdAt)
        )
    }
}