package com.example.seapedia.data.remote.responses.wallet

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class WalletTransactionResponse(
    val id: Int,
    val type: TransactionType,
    val amount: Int,
    val description: String,
    val sender: Int? = null,
    val receiver: Int? = null,
    @SerialName("created_at")
    val createdAt: String
)