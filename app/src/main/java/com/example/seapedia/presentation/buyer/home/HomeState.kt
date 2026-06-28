package com.example.seapedia.presentation.buyer.home

import com.example.seapedia.domain.entities.Product
import com.example.seapedia.domain.entities.Review
import com.example.seapedia.global.utils.CommonState

data class HomeState(
    val searchName: String = "",
    val products: CommonState<List<Product>> = CommonState.Loading(),
    val reviews: CommonState<List<Review>> = CommonState.Loading(),
    val isRefreshing : Boolean = false
)