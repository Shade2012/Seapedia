package com.example.seapedia.data.remote.responses.order

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderPreviewResponse(
    @SerialName("delivery_fee")
    val deliveryFee :Int,
    @SerialName("distance_journey")
    val distanceJourney:Int,
    @SerialName("tax_fee")
    val taxFee :Int,
    @SerialName("sub_total")
    val subTotal :Int,
    @SerialName("total_fee")
    val totalFee :Int,
    @SerialName("voucher_discount")
    val voucherDiscount :Int? = null
)