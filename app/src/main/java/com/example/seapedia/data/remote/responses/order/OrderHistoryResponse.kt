package com.example.seapedia.data.remote.responses.order

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderHistoryResponse(
    val id: Int,
    @SerialName("status_order")
    val status: OrderStatus,
    @SerialName("created_at")
    val createdAt: String
)