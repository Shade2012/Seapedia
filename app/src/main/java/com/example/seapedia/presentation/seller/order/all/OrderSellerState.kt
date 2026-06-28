package com.example.seapedia.presentation.seller.order.all

import com.example.seapedia.data.remote.responses.order.OrderStatus
import com.example.seapedia.domain.entities.Order
import com.example.seapedia.global.utils.CommonState

data class OrderSellerState(
    val allOrders : CommonState<List<Order>> = CommonState.Loading(),
    val returnOrder : List<Order> = listOf(),
    val doneOrder : List<Order> = listOf(),
    val onWayOrder : List<Order> = listOf(),
    val waitingDriverOrder : List<Order> = listOf(),
    val processOrder : List<Order> = listOf(),
    val status : OrderStatus = OrderStatus.All,
    val isRefreshing: Boolean = false,
    val isLoading: Boolean = false,
)
val orderStatusList = listOf<OrderStatus>(
    OrderStatus.All,
    OrderStatus.PROCCESS,
    OrderStatus.WAITING_DRIVER,
    OrderStatus.ON_WAY,
    OrderStatus.DONE,
    OrderStatus.RETURN,
)



