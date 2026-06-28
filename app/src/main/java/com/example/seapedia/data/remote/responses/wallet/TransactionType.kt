package com.example.seapedia.data.remote.responses.wallet

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
enum class TransactionType(val displayName: String) {
    @SerialName("Top Up")
    TOPUP("Top Up"),

    @SerialName("Payment")
    PAYMENT("Payment"),

    @SerialName("Refund")
    REFUND("Refund"),
}