package com.example.seapedia.data.remote.responses.buyer

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BuyerResponse(
    @SerialName("phone_number")
    val phoneNumber: String?
)