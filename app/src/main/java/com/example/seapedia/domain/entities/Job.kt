package com.example.seapedia.domain.entities

import com.example.seapedia.data.remote.responses.order.OrderResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

data class Job @OptIn(ExperimentalTime::class) constructor(
    val id:Int,
    val earning:Int,
    val createdAt: Instant,
    val expiredDate: Instant,
    val isDone: Boolean,
    val order: Order,
)