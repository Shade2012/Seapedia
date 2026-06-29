package com.example.seapedia.domain.entities

import com.example.seapedia.data.remote.responses.carts.DiscountResponse
import com.example.seapedia.data.remote.responses.discount.DiscountType
import kotlinx.serialization.SerialName
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

data class Promo(
    val id: Int,
    val discount: DiscountResponse
)
data class Discount @OptIn(ExperimentalTime::class) constructor(
    val id: Int,
    val name: String,
    val discountPercantage: Int,
    val type: DiscountType,
    val remainingUsage:Int,
    val expiredDate: Instant,
    val maxUsagePerUser: Int
)