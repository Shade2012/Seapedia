package com.example.seapedia.data.remote.responses.discount

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
enum class DiscountType(val displayName: String) {

    @SerialName("Promo")
    PROMO("Promo"),

    @SerialName("Voucher")
    VOUCHER("Voucher"),
}

