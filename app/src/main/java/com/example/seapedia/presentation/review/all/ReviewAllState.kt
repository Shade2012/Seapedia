package com.example.seapedia.presentation.review.all

import com.example.seapedia.domain.entities.Review
import com.example.seapedia.global.utils.CommonState

data class ReviewAllState(
    val reviews: CommonState<List<Review>> = CommonState.Loading(),
    val isRefreshing: Boolean = false,
)