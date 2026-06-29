package com.example.seapedia.data.remote.body

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BuyerPhoneNumberBody(
    @SerialName("phone_number")
    val phoneNumber: String
)