package com.example.seapedia.data.remote.responses.order

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class DeliveryMethod(val displayName: String) {

    @SerialName("Instant")
    INSTANT("Instant"),

    @SerialName("Regular")
    REGULAR("Regular"),

    @SerialName("Next Day")
    NEXT_DAY("Next Day")
}

