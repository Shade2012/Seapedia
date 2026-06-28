package com.example.seapedia.data.repositories

import com.example.seapedia.data.remote.ReviewRemoteDataSources
import com.example.seapedia.data.remote.query.AllReviewQuery
import com.example.seapedia.domain.entities.Review
import com.example.seapedia.domain.entities.toBody
import com.example.seapedia.domain.repositories.ReviewRepository
import com.example.seapedia.global.utils.CommonState
import com.example.seapedia.global.utils.getErrorMessage
import com.example.seapedia.mapper.ReviewRawMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ReviewRepositoryImpl @Inject constructor(
    private val reviewRemoteDataSources: ReviewRemoteDataSources
): ReviewRepository{
    override suspend fun getAllReview(queries: AllReviewQuery): Flow<CommonState<List<Review>>> = flow{
        emit(CommonState.Loading())
        try {
            val response = reviewRemoteDataSources.getAllReview(queries)
            val reviews = response.data.map {
                ReviewRawMapper().mapFromResponse(it)
            }.toList()
            emit(CommonState.Success<List<Review>>(data = reviews))
        } catch (e: retrofit2.HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        } catch (e: Exception) {
            emit(CommonState.Error(message = e.message.toString()))
        }
    }

    override suspend fun createReview(reviewEntity: Review): Flow<CommonState<Review>> = flow{
        emit(CommonState.Loading())
        try {
            val response = reviewRemoteDataSources.createReview(reviewEntity.toBody())
            val review = ReviewRawMapper().mapFromResponse(response.data)
            emit(CommonState.Success<Review>(data = review))
        } catch (e: retrofit2.HttpException) {
            val message = e.getErrorMessage()
            emit(CommonState.Error(message = message))
        } catch (e: Exception) {
            emit(CommonState.Error(message = e.message.toString()))
        }
    }
}