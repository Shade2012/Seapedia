package com.example.seapedia.presentation.seller.home

import com.example.seapedia.domain.entities.Order
import com.example.seapedia.domain.entities.Wallet
import com.example.seapedia.global.utils.CommonState

data class HomeSellerScreenState(
    val orders: CommonState<List<Order>> = CommonState.Loading(),
    val wallet: CommonState<Wallet> = CommonState.Loading(),
    val error: String? = null,
    val isRefreshing : Boolean = false,
)
