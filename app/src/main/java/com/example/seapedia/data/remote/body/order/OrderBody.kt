package com.example.seapedia.data.remote.body.order

import com.example.seapedia.data.remote.responses.order.DeliveryMethod
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderBody (
    @SerialName("delivery_method")
    val deliveryMethod: DeliveryMethod,
    @SerialName("address_id")
    val addressId:Int,
    @SerialName("voucher_code")
    val voucherCode: String? = null
)