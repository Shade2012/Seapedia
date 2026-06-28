package com.example.seapedia.presentation.seller.order.detail

import com.example.seapedia.domain.entities.Order
import com.example.seapedia.domain.entities.OrderHistory
import com.example.seapedia.global.utils.CommonState


data class OrderSellerDetailState (
    val order: CommonState<Order> = CommonState.Loading(),
    val orderHistories: CommonState<List<OrderHistory>> = CommonState.Loading(),

    val isRefreshing : Boolean = false,
){
    val isLoading: Boolean
        get() = order is CommonState.Loading ||
                orderHistories is CommonState.Loading
}