package com.example.seapedia.data.remote.query

import com.example.seapedia.data.remote.responses.order.OrderStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AllOrderQuery(
    @SerialName("order_status")
    val orderStatus: OrderStatus? = null,
): QueryParams