package com.example.seapedia.data.remote

import com.example.seapedia.data.remote.body.ReviewBody
import com.example.seapedia.data.remote.query.AllReviewQuery
import com.example.seapedia.data.remote.query.toMap
import com.example.seapedia.data.remote.responses.BaseResponse
import com.example.seapedia.data.remote.responses.ReviewResponse
import com.example.seapedia.data.remote.services.ReviewService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReviewRemoteDataSources @Inject constructor(
    private val reviewService: ReviewService
) {
    suspend fun getAllReview(queries: AllReviewQuery): BaseResponse<List<ReviewResponse>> = reviewService.getAllReview(queries.toMap())
    suspend fun createReview(reviewBody: ReviewBody): BaseResponse<ReviewResponse> = reviewService.createReview(reviewBody)
}