package com.example.seapedia.domain.usecases.review

import com.example.seapedia.data.remote.query.AllReviewQuery
import com.example.seapedia.domain.entities.ReviewEntity
import com.example.seapedia.domain.repositories.ReviewRepository
import com.example.seapedia.global.utils.CommonState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllReviewUseCase @Inject constructor(
    private val reviewRepository: ReviewRepository
) {
    suspend fun run(queries: AllReviewQuery) : Flow<CommonState<List<ReviewEntity>>>{
        return reviewRepository.getAllReview(queries)
    }
}