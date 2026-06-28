package com.example.seapedia.data.remote.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JobResponse(
    val id:Int,
    @SerialName("expired_date")
    val expiredDate: String,
    val earning:Int,
    @SerialName("is_done")
    val isDone: Boolean,
    val driver: DriverResponse?
)