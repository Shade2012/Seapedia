package com.example.seapedia.domain.repositories

import com.example.seapedia.data.remote.query.AllReviewQuery
import com.example.seapedia.domain.entities.Review
import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.flow.Flow

interface ReviewRepository {
    suspend fun getAllReview(queries: AllReviewQuery): Flow<CommonState<List<Review>>>
    suspend fun createReview(
        review: Review
    ): Flow<CommonState<Review>>
}