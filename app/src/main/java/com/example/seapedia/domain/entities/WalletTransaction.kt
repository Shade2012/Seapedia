package com.example.seapedia.domain.entities

import com.example.seapedia.data.remote.responses.wallet.TransactionType
import kotlin.time.ExperimentalTime
import kotlin.time.Instant


data class WalletTransaction @OptIn(ExperimentalTime::class) constructor(
    val id: Int,
    val type: TransactionType,
    val amount: Int,
    val description: String,
    val sender: Int? = null,
    val receiver: Int? = null,
    val createdAt: Instant
)