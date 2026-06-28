package com.example.seapedia.data.remote.body

import com.example.seapedia.data.remote.responses.order.OrderStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderStatusBody(
    @SerialName("order_status")
    val orderStatus : OrderStatus
)