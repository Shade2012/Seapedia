package com.example.seapedia.domain.entities

import com.example.seapedia.data.remote.responses.order.OrderStatus
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

data class OrderHistory @OptIn(ExperimentalTime::class) constructor(
    val id: Int,
    val status: OrderStatus,
    val createdAt: Instant
)