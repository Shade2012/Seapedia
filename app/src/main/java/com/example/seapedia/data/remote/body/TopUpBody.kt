package com.example.seapedia.data.remote.body

import kotlinx.serialization.Serializable

@Serializable
data class TopUpBody(
    val amount: Int
)