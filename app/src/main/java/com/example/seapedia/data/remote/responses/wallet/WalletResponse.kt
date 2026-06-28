package com.example.seapedia.data.remote.responses.wallet

import kotlinx.serialization.Serializable

@Serializable
data class WalletResponse(
    val id:Int,
    val balance: Int
)