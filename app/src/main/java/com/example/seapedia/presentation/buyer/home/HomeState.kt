package com.example.seapedia.presentation.buyer.home

import com.example.seapedia.domain.entities.Product
import com.example.seapedia.domain.entities.Review
import com.example.seapedia.domain.entities.Wallet
import com.example.seapedia.global.utils.CommonState
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

data class HomeState @OptIn(ExperimentalTime::class) constructor(
    val searchName: String = "",
    val bottomSheetLoading: Boolean? = null,
    val daySystem: Instant? = null,
    val wallet: CommonState<Wallet> = CommonState.Loading(),
    val spending: CommonState<Int> = CommonState.Loading(),
    val products: CommonState<List<Product>> = CommonState.Loading(),
    val reviews: CommonState<List<Review>> = CommonState.Loading(),
    val isRefreshing : Boolean = false
)