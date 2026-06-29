package com.example.seapedia.presentation.buyer.home

import com.example.seapedia.domain.entities.Product
import com.example.seapedia.domain.entities.Review
import com.example.seapedia.domain.entities.Wallet
import com.example.seapedia.global.utils.CommonState

data class HomeState(
    val searchName: String = "",
    val bottomSheetLoading: Boolean? = null,
    val wallet: CommonState<Wallet> = CommonState.Loading(),
    val spending: CommonState<Int> = CommonState.Loading(),
    val products: CommonState<List<Product>> = CommonState.Loading(),
    val reviews: CommonState<List<Review>> = CommonState.Loading(),
    val isRefreshing : Boolean = false
)