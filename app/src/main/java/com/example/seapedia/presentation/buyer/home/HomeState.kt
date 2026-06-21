package com.example.seapedia.presentation.buyer.home

import com.example.seapedia.domain.entities.ProductEntity
import com.example.seapedia.domain.entities.ReviewEntity
import com.example.seapedia.global.utils.CommonState

data class HomeState(
    val searchName: String = "",
    val products: CommonState<List<ProductEntity>> = CommonState.Loading(),
    val reviews: CommonState<List<ReviewEntity>> = CommonState.Loading(),
    val isRefreshing : Boolean = false
)