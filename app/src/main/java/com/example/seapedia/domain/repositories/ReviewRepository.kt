package com.example.seapedia.domain.repositories

import com.example.seapedia.data.remote.query.AllReviewQuery
import com.example.seapedia.domain.entities.ReviewEntity
import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.flow.Flow

interface ReviewRepository {
    suspend fun getAllReview(queries: AllReviewQuery): Flow<CommonState<List<ReviewEntity>>>
    suspend fun createReview(
        reviewEntity: ReviewEntity
    ): Flow<CommonState<ReviewEntity>>
}