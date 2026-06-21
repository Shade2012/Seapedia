package com.example.seapedia.presentation.review.all

import com.example.seapedia.domain.entities.ReviewEntity
import com.example.seapedia.global.utils.CommonState

data class ReviewAllState(
    val reviews: CommonState<List<ReviewEntity>> = CommonState.Loading(),
    val isRefreshing: Boolean = false,
)