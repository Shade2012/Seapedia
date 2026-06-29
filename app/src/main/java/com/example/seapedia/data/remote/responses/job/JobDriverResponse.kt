package com.example.seapedia.data.remote.responses.job

import com.example.seapedia.data.remote.responses.order.OrderResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JobDriverResponse(
    val id: Int,
    val earning: Int,
    @SerialName("expired_date")
    val expiredDate: String,
    @SerialName("is_done")
    val isDone: Boolean,
    @SerialName("created_at")
    val createdAt: String,
    val order : OrderResponse
)