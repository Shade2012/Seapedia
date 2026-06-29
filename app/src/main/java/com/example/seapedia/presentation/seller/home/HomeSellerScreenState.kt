package com.example.seapedia.presentation.seller.home

import com.example.seapedia.domain.entities.Order
import com.example.seapedia.global.utils.CommonState
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

data class HomeSellerScreenState @OptIn(ExperimentalTime::class) constructor(
    val orders: CommonState<List<Order>> = CommonState.Loading(),
    val income: CommonState<Int> = CommonState.Loading(),
    val error: String? = null,
    val daySystem : Instant? = null,
    val isRefreshing : Boolean = false,
)
