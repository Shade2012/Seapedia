package com.example.seapedia.presentation.review.all

import com.example.seapedia.domain.entities.Review
import com.example.seapedia.global.utils.CommonState
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

data class ReviewAllState @OptIn(ExperimentalTime::class) constructor(
    val reviews: CommonState<List<Review>> = CommonState.Loading(),
    val daySystem : Instant? = null,
    val isRefreshing: Boolean = false,
)